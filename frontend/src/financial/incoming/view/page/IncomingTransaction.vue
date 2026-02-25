<script lang="ts" setup>
import { PageResponse } from '@/common/model/page.response';
import { useHandlerMessage } from '@/composoable/commons';
import { AccountResponse } from '@/financial/account/model/account.response';
import accountService from '@/financial/account/service/account.service';
import { CategoryFilter } from '@/financial/category/model/category.filter';
import { CategoryResponse } from '@/financial/category/model/category.response.mode';
import categoryService from '@/financial/category/service/category.service';
import { AxiosError } from 'axios';
import { format } from 'date-fns';
import { DatePicker, Drawer } from 'primevue';
import { onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { Incoming } from '../../model/incoming';
import { IncomingResponse } from '../../model/incoming.response';
import IncomingTransactionService from '../../service/incoming.transaction.service';

const { t } = useI18n();
const pageIncomingResult = ref<PageResponse<IncomingResponse> | null>();
const incomingSelected = ref<IncomingResponse | any>();
const handlerMessage = useHandlerMessage();
const incoming = ref<Incoming>({} as Incoming);
const pageAccountResult = ref<PageResponse<AccountResponse> | null>();
const pageCategoryResult = ref<PageResponse<CategoryResponse> | any>();
const categoryFilter = ref<CategoryFilter | null>(null);
const visibleCadIncoming = ref<boolean>(false);
const accountSelected = ref<AccountResponse | null>();
const categorySelected = ref<CategoryResponse | null>();
const loadingSave = ref<boolean>(false);

onMounted(async () => {
    await findByFilter();
});

const home = ref({
    icon: 'pi pi-home',
    route: '/'
});
const items = ref([
    { label: 'Transações' },
    { label: 'Entradas', route: '/transaction/incoming' }
]);

const findByFilter = async () => {
    try {
        pageIncomingResult.value = await IncomingTransactionService.findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
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

const openCadIncoming = async () => {
    visibleCadIncoming.value = true;
    incoming.value = {} as Incoming;
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
        visibleCadIncoming.value = false;
        await findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loadingSave.value = false;
    }
};

const formatCurrency = (value: any) => {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
};
</script>
<template>
    <Toolbar class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl mb-2">
        <template #start>
            <Button :label="t('new')" icon="pi pi-plus" severity="info" class="mr-2" @click="openCadIncoming" />
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
    <div class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl">
        <DataTable ref="dt" :value="pageIncomingResult?.content" stripedRows>
            <Column field="description" :header="t('description')" sortable style="min-width: 12rem" />
            <Column field="paymentAt" :header="t('paymentAt')" sortable style="min-width: 12rem">
                <template #body="slotProps">
                    {{ format(slotProps.data.paymentAt, 'dd/MM/yyyy') }}
                </template>
            </Column>
            <Column field="amount" :header="t('amount')" sortable style="min-width: 12rem">
                <template #body="slotProps">
                    {{ formatCurrency(slotProps.data.amount) }}
                </template>
            </Column>
            <Column field="category" :header="t('category')" sortable style="min-width: 12rem" />
            <Column field="account" :header="t('account')" sortable style="min-width: 12rem" />
        </DataTable>
        <Drawer :header="t('transactionIncoming')" v-model:visible="visibleCadIncoming" :dismissable="false"
            position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
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
                <Select :options="pageCategoryResult?.content" option-label="name" @vue:before-mount="findCategory"
                    fluid v-model="categorySelected" show-clear />
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
                <Button type="submit" severity="danger" :label="t('cancel')"
                    @click.prevent="visibleCadIncoming = false" />
            </div>
        </Drawer>
    </div>
</template>
