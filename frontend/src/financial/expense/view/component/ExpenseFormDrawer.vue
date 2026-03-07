<script lang="ts" setup>
import { PageResponse } from '@/common/model/page.response';
import { useHandlerMessage } from '@/composoable/commons';
import { AccountInfoResponse } from '@/financial/account/model/account.info.response.model';
import { AccountResponse } from '@/financial/account/model/account.response';
import accountService from '@/financial/account/service/account.service';
import { CategoryFilter } from '@/financial/category/model/category.filter';
import { CategoryResponse } from '@/financial/category/model/category.response.mode';
import { CategoryType } from '@/financial/category/model/category.type';
import categoryService from '@/financial/category/service/category.service';
import { AxiosError } from 'axios';
import { ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { ExpenseTransaction } from '../../model/expense';
import expenseService from '../../service/expense.service';

const { t } = useI18n();
const handlerMessage = useHandlerMessage();

const expense = ref<ExpenseTransaction>({} as ExpenseTransaction);
const accountSelected = ref<AccountInfoResponse | null>();
const categorySelected = ref<CategoryResponse | null>();
const pageAccountResult = ref<PageResponse<AccountResponse> | null>();
const pageCategoryResult = ref<PageResponse<CategoryResponse> | any>();
const loadingSave = ref<boolean>();
const validSubmit = ref<boolean>(true);
const categoryFilter = ref<CategoryFilter>({
    keyword: '',
    limit: 10,
    page: 0,
    categoryType: CategoryType.EXPENSE
});

const props = defineProps<{
    visible: boolean
}>();

watch(() => props.visible, (newVal) => {
    if (newVal) {
        clear();
    }
});

const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void;
    (e: 'saved'): void;
}>();

const close = () => {
    emit("update:visible", false);
}

const findCategory = async () => {
    try {
        console.log(categoryFilter.value);
        pageCategoryResult.value = await categoryService.findByFilter(categoryFilter.value);
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
}

const findAccount = async () => {
    try {
        pageAccountResult.value = await accountService.findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
}

const validateEmptyRequiredForm = async () => {
    validSubmit.value = !(expense.value.description === ''
        || accountSelected.value === null || categorySelected.value === null
        || expense.value.dueDate === null
        || expense.value.paymentAt === null || expense.value.amount === 0
    );
}

const save = async () => {
    try {
        loadingSave.value = true;

        await validateEmptyRequiredForm();
        if (!validSubmit.value) return;

        if (accountSelected.value?.id) expense.value.accountId = accountSelected.value?.id;
        if (categorySelected.value?.id) expense.value.categoryId = categorySelected.value?.id;

        await expenseService.save(expense.value);
        handlerMessage.toastSuccess(t('expenseSavedSuccess'));
        close();
        emit('saved');

    } catch (error: any) {
        handlerMessage.error(error);
    } finally {
        loadingSave.value = false;
    }
}

const clear = () => {
    expense.value = {
        description: '',
        amount: 0,
        dueDate: null,
        categoryId: null,
        accountId: null,
        type: '',
        status: ''
    } as ExpenseTransaction;
    accountSelected.value = null;
    categorySelected.value = null;
    validSubmit.value = true;
}

</script>

<template>
    <Drawer :header="t('expenseRegister')" :visible="visible" @update:visible="emit('update:visible', $event)"
        :dismissable="false" position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
        <div class="flex flex-col gap-1 mt-2">
            <label for="desc" class="font-bold">{{ t('description') }}</label>
            <InputText id="desc" v-model="expense.description" />
            <Message v-show="expense.description === '' && validSubmit === false" severity="error" variant="simple"
                size="small">{{ t('descriptionRequired') }}</Message>
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label class="font-bold">{{ t('account') }}</label>
            <Select :options="pageAccountResult?.content" option-label="accountName" @vue:before-mount="findAccount"
                fluid v-model="accountSelected" show-clear />
            <Message v-show="accountSelected === null && validSubmit === false" severity="error" variant="simple"
                size="small">{{ t('accountRequired') }}</Message>
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label class="font-bold">{{ t('category') }}</label>
            <Select :options="pageCategoryResult?.content" option-label="name" @vue:before-mount="findCategory" fluid
                v-model="categorySelected" show-clear />
            <Message v-show="categorySelected === null && validSubmit === false" severity="error" variant="simple"
                size="small">{{ t('categoryRequired') }}</Message>
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label for="date" class="font-bold">{{ t('dueDate') }}</label>
            <DatePicker id="date" v-model="expense.dueDate" dateFormat="dd/mm/yy" showIcon fluid />
            <Message v-show="expense.dueDate === null && validSubmit === false" severity="error" variant="simple"
                size="small">{{ t('dueDateRequired') }}</Message>
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label for="date" class="font-bold">{{ t('paymentAt') }}</label>
            <DatePicker id="date" v-model="expense.paymentAt" dateFormat="dd/mm/yy" showIcon fluid />
        </div>
        <div class="flex flex-col gap-1 mt-2">
            <label for="amount" class="font-bold">{{ t('amount') }}</label>
            <InputNumber id="amount" v-model="expense.amount" mode="currency" currency="BRL" locale="pt-BR" />
        </div>
        <div class="flex flex-col gap-1 mt-5">
            <Button type="submit" severity="info" :label="t('save')" @click.prevent="save" />
            <Button type="submit" severity="danger" :label="t('cancel')" @click="close" />
        </div>

    </Drawer>
</template>