<script setup lang="ts">
import { PageResponse } from '@/common/model/page.response';
import { useHandlerMessage } from '@/composoable/commons';
import { AxiosError } from 'axios';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { CategoryFilter } from '../../model/category.filter';
import { Category } from '../../model/category.model';
import { CategoryResponse } from '../../model/category.response.mode';
import { CategoryType } from '../../model/category.type';
import categoryService from '../../service/category.service';

const pageResult = ref<PageResponse<CategoryResponse> | null>(null);
const handlerMessage = useHandlerMessage();
const categorySelected = ref<CategoryResponse | null>(null);
const loading = ref<boolean>(false);
const visibleDrawerCadCategory = ref<boolean>(false);
const filterCategory = ref<CategoryFilter | null>(null);
const category = ref<Category>({} as Category);
const validSubmit = ref<boolean>(true);
const treeNodes = ref();
const loadingSave = ref<boolean>(false);
const loadingEdit = ref<boolean>(false);
const { t } = useI18n();

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

onBeforeMount(async () => {
    await findByFilter();
});

const typeOptions = ref([
    { label: t('REVENUE'), value: CategoryType.REVENUE },
    { label: t('EXPENSE'), value: CategoryType.EXPENSE }
]);

const findByFilter = async () => {
    try {
        loading.value = true;
        pageResult.value = await categoryService.findByFilter(filterCategory.value);
        treeNodes.value = pageResult.value?.content.map((item) => ({
            key: item.id,
            leaf: false,
            data: {
                ...item,
                children: []
            }
        }));
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loading.value = false;
    }
};

const findById = async (id: string) => {
    try {
        loadingEdit.value = true;
        clearCategory();

        visibleDrawerCadCategory.value = true;
        await delay(1000);
        const response = await categoryService.findById(id);
        openCadCategory(response);
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
        visibleDrawerCadCategory.value = false;
    } finally {
        loadingEdit.value = false;
    }
};

const save = async () => {
    try {
        loadingSave.value = true;

        await validateEmptyRequiredForm();
        if (!validSubmit.value) return;

        await categoryService.save(category.value);
        handlerMessage.toastSuccess(t('categorySavedSuccess'));
        visibleDrawerCadCategory.value = false;
        await findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loadingSave.value = false;
    }
};

const validateEmptyRequiredForm = async () => {
    validSubmit.value = !(category.value.name === '' || category.value.colorHex === '' || category.value.iconCode === '');
};

const openCadCategory = (categoryReturn: Category | null) => {
    validSubmit.value = true;
    visibleDrawerCadCategory.value = true;

    if (categoryReturn) {
        category.value = categoryReturn;
    } else {
        clearCategory();
    }
};

const openNewCadCategoryForParent = (parentId: string) => {
    try {
        clearCategory();
        category.value.parentId = parentId;
        visibleDrawerCadCategory.value = true;
    } catch (error) {}
};

const clearCategory = () => {
    category.value = { colorHex: 'ff0808', iconCode: '', name: '', categoryType: CategoryType.REVENUE } as Category;
};

const onExpandRow = async (node: any) => {
    try {
        if (node.children && node.children.length > 0) return;

        loading.value = true;
        const response = await categoryService.findByParentId(node.key);

        const newChildren = response.map((item: any) => ({
            key: item.id,
            data: { ...item }
        }));

        const updateRecursive = (nodes: any[]) => {
            for (let n of nodes) {
                if (n.key === node.key) {
                    n.children = newChildren;
                    return true;
                }
                if (n.children && updateRecursive(n.children)) return true;
            }
            return false;
        };

        updateRecursive(treeNodes.value);

        treeNodes.value = [...treeNodes.value];
    } catch (error: any) {
        handlerMessage.error(error);
    } finally {
        loading.value = false;
    }
};
</script>
<template>
    <div class="card">
        <Toolbar class="mb-6">
            <template #start>
                <Button :label="t('new')" icon="pi pi-plus" severity="info" class="mr-2" @click="openCadCategory(null)" />
            </template>
        </Toolbar>
        <TreeTable :value="treeNodes" scrollable scrollHeight="45rem" @node-expand="onExpandRow" :lazy="true" :loading="loading">
            <Column field="name" :header="t('description')" :expander="true" style="width: 30%" frozen></Column>
            <Column :header="t('icon')">
                <template #body="slotProps">
                    <div class="flex flex-wrap gap-2">
                        <i :class="slotProps.node.data.iconCode ? slotProps.node.data.iconCode : 'pi pi-tag'" :style="{ color: '#' + (slotProps.node.data.colorHex || '999') }" class="mr-2"> </i>
                    </div>
                </template>
            </Column>

            <Column :header="t('type')">
                <template #body="slotProps">
                    <Tag v-if="slotProps.node.data.categoryType" :value="t(slotProps.node.data.categoryType)" severity="secondary" class="ml-2" style="font-size: 10px"> </Tag>
                    <Tag v-else value="N/A" severity="secondary" class="ml-2" style="font-size: 10px"> </Tag>
                </template>
            </Column>
            <Column style="width: 10rem" :header="t('actions')">
                <template #body="slotProps">
                    <div class="flex flex-wrap gap-2">
                        <Button type="button" icon="pi pi-pencil" rounded severity="info" @click="findById(slotProps.node.data.id)" />
                        <Button type="button" icon="pi pi-plus" rounded @click="openNewCadCategoryForParent(slotProps.node.data.id)" v-if="!slotProps.node.data.parentId" v-tooltip="{ value: t('addSubCategory'), showDelay: 200 }" />
                    </div>
                </template>
            </Column>
        </TreeTable>
        <Drawer v-model:visible="visibleDrawerCadCategory" :header="t('category')" position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
            <div v-if="loadingEdit">
                <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                <div class="flex flex-col gap-1 mt-5">
                    <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                    <Skeleton class="mb-2" borderRadius="16px"></Skeleton>
                </div>
            </div>
            <div v-else>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="category-name">{{ t('description') }}</label>
                    <InputText id="category-name" v-model="category.name" type="text" fluid />
                    <Message v-show="category.name === '' && validSubmit === false" severity="error" variant="simple" size="small">Informe o nome da categoria</Message>
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>{{ t('selectIcon') }}</label>
                    <IconPicker name="iconCode" v-model="category.iconCode" />
                    <Message v-show="category.iconCode === '' && validSubmit === false" severity="error" variant="simple" size="small">Selecione um icone</Message>
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>{{ t('selectColor') }}</label>
                    <ColorPicker name="colorHex" v-model="category.colorHex" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>Tipo de Categoria</label>
                    <SelectButton v-model="category.categoryType" :options="typeOptions" optionLabel="label" optionValue="value" />
                </div>
                <div class="flex flex-col gap-1 mt-5">
                    <Button type="submit" severity="info" :label="t('save')" @click.prevent="save" :loading="loadingSave" />
                    <Button type="submit" severity="danger" :label="t('cancel')" @click.prevent="visibleDrawerCadCategory = false" />
                </div>
            </div>
        </Drawer>
    </div>
</template>
