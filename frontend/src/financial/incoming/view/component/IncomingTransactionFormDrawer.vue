<script lang="ts" setup>
import { PageResponse } from '@/common/model/page.response';
import { useHandlerMessage } from '@/composoable/commons';
import { AccountResponse } from '@/financial/account/model/account.response';
import accountService from '@/financial/account/service/account.service';
import { CategoryFilter } from '@/financial/category/model/category.filter';
import { CategoryResponse } from '@/financial/category/model/category.response.mode';
import { CategoryType } from '@/financial/category/model/category.type';
import categoryService from '@/financial/category/service/category.service';
import { AxiosError } from 'axios';
import { DatePicker, Drawer } from 'primevue';
import { ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { Incoming } from '../../model/incoming';
import IncomingTransactionService from '../../service/incoming.transaction.service';

const { t } = useI18n();
const handlerMessage = useHandlerMessage();

const props = defineProps<{
    visible: boolean;
}>();

const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void;
    (e: 'saved'): void;
}>();

const incoming = ref<Incoming>({} as Incoming);
const pageAccountResult = ref<PageResponse<AccountResponse> | null>();
const pageCategoryResult = ref<PageResponse<CategoryResponse> | any>();
const accountSelected = ref<AccountResponse | null>();
const categorySelected = ref<CategoryResponse | null>();
const loadingSave = ref<boolean>(false);

const categoryFilter = ref<CategoryFilter>({
    keyword: '',
    limit: 10,
    page: 0,
    categoryType: CategoryType.REVENUE
});
watch(() => props.visible, (newVal) => {
    if (newVal) {
        incoming.value = {} as Incoming;
        accountSelected.value = null;
        categorySelected.value = null;
    }
});

const close = () => {
    emit('update:visible', false);
};

const findCategory = async () => {
    try {
        pageCategoryResult.value = await categoryService.findByFilter(categoryFilter.value);
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
};

const findAccount = async () => {
    try {
        pageAccountResult.value = await accountService.findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
};

const save = async () => {
    try {
        loadingSave.value = true;
        if (accountSelected.value) {
            incoming.value.accountId = accountSelected.value?.id;
        }
        if (categorySelected.value) {
            incoming.value.categoryId = categorySelected.value?.id;
        }

        await IncomingTransactionService.save(incoming.value);
        handlerMessage.toastSuccess(t('incomingTransactionSavedSuccess'));
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
    <Drawer :header="t('transactionIncoming')" :visible="visible" @update:visible="emit('update:visible', $event)"
        :dismissable="false" position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
        <div class="flex flex-col gap-1 mt-2">
            <label for="desc" class="font-bold">{{ t('description') }}</label>
            <InputText id="desc" v-model="incoming.description" />
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label class="font-bold">{{ t('account') }}</label>
            <Select :options="pageAccountResult?.content" option-label="accountName" @vue:before-mount="findAccount"
                fluid v-model="accountSelected" show-clear />
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label class="font-bold">{{ t('category') }}</label>
            <Select :options="pageCategoryResult?.content" option-label="name" @vue:before-mount="findCategory" fluid
                v-model="categorySelected" show-clear />
        </div>

        <div class="flex flex-col gap-1 mt-2">
            <label for="amount" class="font-bold">{{ t('amount') }}</label>
            <InputNumber id="amount" v-model="incoming.amount" mode="currency" currency="BRL" locale="pt-BR" />
        </div>

        <div class="flex flex-col gap-1 mt-2">
            <label for="date" class="font-bold">{{ t('paymentAt') }}</label>
            <DatePicker id="date" v-model="incoming.paymentAt" dateFormat="dd/mm/yy" showIcon fluid />
        </div>
        <div class="flex flex-col gap-1 mt-5">
            <Button type="submit" severity="info" :label="t('save')" @click="save" :loading="loadingSave" />
            <Button type="submit" severity="danger" :label="t('cancel')" @click.prevent="close" />
        </div>
    </Drawer>
</template>
