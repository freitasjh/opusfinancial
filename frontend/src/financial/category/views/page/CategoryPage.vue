<script setup lang="ts">
import { PageResponse } from '@/common/model/page.response';
import { useHandlerMessage } from '@/composoable/commons';
import { AxiosError } from 'axios';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { CategoryFilter } from '../../model/category.filter';
import { CategoryResponse } from '../../model/category.response.mode';
import categoryService from '../../service/category.service';
import CategoryFormDrawer from '../components/CategoryFormDrawer.vue';

const pageResult = ref<PageResponse<CategoryResponse> | null>(null);
const handlerMessage = useHandlerMessage();
const categorySelected = ref<CategoryResponse | null>(null);
const loading = ref<boolean>(false);
const visibleDrawerCadCategory = ref<boolean>(false);
const selectedCategoryId = ref<string | null>(null);
const selectedParentId = ref<string | null>(null);
const filterCategory = ref<CategoryFilter | null>(null);
const treeNodes = ref();
const { t } = useI18n();

const home = ref({
    icon: 'pi pi-home',
    route: '/'
});
const items = ref([
    { label: t('register') },
    { label: t('category'), route: '/category' }
]);

onBeforeMount(async () => {
    await findByFilter();
});

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

const findById = (id: string) => {
    selectedCategoryId.value = id;
    selectedParentId.value = null;
    visibleDrawerCadCategory.value = true;
};

const openCadCategory = () => {
    selectedCategoryId.value = null;
    selectedParentId.value = null;
    visibleDrawerCadCategory.value = true;
};

const openNewCadCategoryForParent = (parentId: string) => {
    selectedCategoryId.value = null;
    selectedParentId.value = parentId;
    visibleDrawerCadCategory.value = true;
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
    <Toolbar class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl mb-4 border border-surface-200 dark:border-surface-800">
        <template #start>
            <Button :label="t('new')" icon="pi pi-plus" severity="primary" class="mr-2" @click="openCadCategory()" />
        </template>
        <template #end>
            <div class="flex items-center gap-2">
                <Breadcrumb :home="home" :model="items">
                    <template #item="{ item, props }">
                        <router-link v-if="item.route" v-slot="{ href, navigate }" :to="item.route" custom>
                            <a :href="href" v-bind="props.action" @click="navigate">
                                <span :class="[item.icon, 'text-color']" />
                                <span class="text-primary font-semibold">{{ item.label }}</span>
                            </a>
                        </router-link>
                        <a v-else :href="item.url" :target="item.target" v-bind="props.action">
                            <span class="text-surface-700 dark:text-surface-0">{{ item.label }}</span>
                        </a>
                    </template>
                </Breadcrumb>
            </div>
        </template>
    </Toolbar>

    <div class="bg-surface-0 dark:bg-surface-900 shadow-sm p-6 rounded-2xl border border-surface-200 dark:border-surface-800">

        <TreeTable :value="treeNodes" scrollable scrollHeight="45rem" @node-expand="onExpandRow" :lazy="true"
            stripedRows :loading="loading">
            <template #empty>
                <div class="flex flex-col items-center justify-center p-8 gap-4">
                    <i class="pi pi-inbox text-surface-400" style="font-size: 3rem"></i>
                    <div class="text-surface-500 font-semibold">{{ t('empty') }}</div>
                </div>
            </template>
            <Column field="name" :header="t('description')" :expander="true" style="width: 30%" frozen></Column>
            <Column :header="t('icon')">
                <template #body="slotProps">
                    <div class="flex flex-wrap gap-2">
                        <i :class="slotProps.node.data.iconCode ? slotProps.node.data.iconCode : 'pi pi-tag'"
                            :style="{ color: '#' + (slotProps.node.data.colorHex || '999') }" class="mr-2"> </i>
                    </div>
                </template>
            </Column>

            <Column :header="t('type')">
                <template #body="slotProps">
                    <Tag v-if="slotProps.node.data.categoryType" :value="t(slotProps.node.data.categoryType)"
                        severity="secondary" class="ml-2" style="font-size: 10px"> </Tag>
                    <Tag v-else value="N/A" severity="secondary" class="ml-2" style="font-size: 10px"> </Tag>
                </template>
            </Column>
            <Column style="width: 10rem" :header="t('actions')">
                <template #body="slotProps">
                    <div class="flex flex-wrap gap-2">
                        <Button type="button" icon="pi pi-pencil" rounded severity="info"
                            @click="findById(slotProps.node.data.id)" />
                        <Button type="button" icon="pi pi-plus" rounded
                            @click="openNewCadCategoryForParent(slotProps.node.data.id)"
                            v-if="!slotProps.node.data.parentId"
                            v-tooltip="{ value: t('addSubCategory'), showDelay: 200 }" />
                    </div>
                </template>
            </Column>
        </TreeTable>
        <CategoryFormDrawer v-model:visible="visibleDrawerCadCategory" :categoryId="selectedCategoryId"
            :parentId="selectedParentId" @saved="findByFilter" />
    </div>
</template>
