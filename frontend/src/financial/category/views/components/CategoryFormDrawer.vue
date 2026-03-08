<script setup lang="ts">
import { useHandlerMessage } from '@/composoable/commons';
import { AxiosError } from 'axios';
import { ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import AppFormDrawer from '@/components/AppFormDrawer.vue';
import { Category } from '../../model/category.model';
import { CategoryType } from '../../model/category.type';
import categoryService from '../../service/category.service';

const { t } = useI18n();
const handlerMessage = useHandlerMessage();

const props = defineProps<{
    visible: boolean;
    categoryId?: string | null;
    parentId?: string | null;
}>();

const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void;
    (e: 'saved'): void;
}>();

const category = ref<Category>({ colorHex: 'ff0808', iconCode: '', name: '', categoryType: CategoryType.REVENUE } as Category);
const validSubmit = ref<boolean>(true);
const loadingSave = ref<boolean>(false);
const loadingEdit = ref<boolean>(false);

const typeOptions = ref([
    { label: t('REVENUE'), value: CategoryType.REVENUE },
    { label: t('EXPENSE'), value: CategoryType.EXPENSE }
]);

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

const clearCategory = () => {
    category.value = { colorHex: 'ff0808', iconCode: '', name: '', categoryType: CategoryType.REVENUE } as Category;
    validSubmit.value = true;
};

watch(() => props.visible, async (newVal) => {
    if (newVal) {
        clearCategory();
        if (props.categoryId) {
            await findById(props.categoryId);
        } else if (props.parentId) {
            category.value.parentId = props.parentId;
        }
    }
});

const close = () => {
    emit('update:visible', false);
};

const findById = async (id: string) => {
    try {
        loadingEdit.value = true;
        await delay(1000);
        const response = await categoryService.findById(id);
        category.value = response;
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
        close();
    } finally {
        loadingEdit.value = false;
    }
};

const validateEmptyRequiredForm = async () => {
    validSubmit.value = !(category.value.name === '' || category.value.colorHex === '' || category.value.iconCode === '');
};

const save = async () => {
    try {
        loadingSave.value = true;

        await validateEmptyRequiredForm();
        if (!validSubmit.value) return;

        await categoryService.save(category.value);
        handlerMessage.toastSuccess(t('categorySavedSuccess'));
        close();
        emit('saved');
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loadingSave.value = false;
    }
};

</script>

<template>
    <AppFormDrawer 
        :visible="visible" 
        @update:visible="emit('update:visible', $event)" 
        :header="t('category')" 
        :loading="loadingSave"
        @save="save"
        @cancel="close"
    >
        <div v-if="loadingEdit">
            <Skeleton class="mb-4" height="3rem" borderRadius="16px"></Skeleton>
            <Skeleton class="mb-4" height="3rem" borderRadius="16px"></Skeleton>
            <Skeleton class="mb-4" height="3rem" borderRadius="16px"></Skeleton>
            <Skeleton class="mb-4" height="3rem" borderRadius="16px"></Skeleton>
        </div>
        <div v-else class="flex flex-col gap-4 mt-2">
            <div class="flex flex-col gap-1">
                <label for="category-name" class="font-medium text-sm">{{ t('description') }}</label>
                <InputText id="category-name" v-model="category.name" type="text" fluid />
                <Message v-show="category.name === '' && validSubmit === false" severity="error" variant="simple"
                    size="small">{{ t('categoryNameRequired') }}</Message>
            </div>
            <div class="flex flex-col gap-1">
                <label class="font-medium text-sm">{{ t('selectIcon') }}</label>
                <IconPicker name="iconCode" v-model="category.iconCode" />
                <Message v-show="category.iconCode === '' && validSubmit === false" severity="error" variant="simple"
                    size="small">{{ t('iconRequired') }}</Message>
            </div>
            <div class="flex flex-col gap-1">
                <label class="font-medium text-sm">{{ t('selectColor') }}</label>
                <ColorPicker name="colorHex" v-model="category.colorHex" />
            </div>
            <div class="flex flex-col gap-1">
                <label class="font-medium text-sm">{{ t('categoryType') }}</label>
                <SelectButton v-model="category.categoryType" :options="typeOptions" optionLabel="label"
                    optionValue="value" class="w-full" />
            </div>
        </div>
    </AppFormDrawer>
</template>
