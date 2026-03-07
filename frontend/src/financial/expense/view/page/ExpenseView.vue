<script setup lang="ts">
import { PageResponse } from '@/common/model/page.response';
import { formatCurrency, formatDate, useHandlerMessage } from '@/composoable/commons';
import { FilterMatchMode } from '@primevue/core/api';
import { AxiosError } from 'axios';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { ExpenseInfo } from '../../dto/expense.info';
import expenseService from '../../service/expense.service';
import ExpenseFormDrawer from '../component/ExpenseFormDrawer.vue';
const { t } = useI18n();

const expensePageResult = ref<PageResponse<ExpenseInfo>>();

const handlerMessage = useHandlerMessage();
const loadingFindExpense = ref<boolean>(false);
const visibleCad = ref<boolean>(false);

onBeforeMount(async () => {
    await findByFilter();
});

const home = ref({
    icon: 'pi pi-home',
    route: '/'
});

const items = ref([
    {
        label: t('transaction'),
        route: ''
    },
    {
        label: t('expense'),
        route: ''
    }
]);

const filters = ref({
    'global': { value: null, matchMode: FilterMatchMode.CONTAINS },
});

const findByFilter = async () => {
    try {
        loadingFindExpense.value = true;
        expensePageResult.value = await expenseService.findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loadingFindExpense.value = false;
    }
};

</script>

<template>
    <Toolbar class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl mb-2">
        <template #start>
            <Button :label="t('new')" icon="pi pi-plus" severity="info" class="mr-2" @click="visibleCad = true" />
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
        <DataTable ref="dt" :value="expensePageResult?.content" stripedRows :loading="loadingFindExpense">
            <template #header>
                <div class="flex flex-wrap gap-2 items-center justify-between">
                    <h4 class="m-0">{{ t('expense') }}</h4>
                    <IconField>
                        <InputIcon>
                            <i class="pi pi-search" />
                        </InputIcon>
                        <InputText v-model="filters['global'].value" :placeholder="t('search')" />
                    </IconField>
                </div>
            </template>
            <template #empty>
                <div class="flex flex-col items-center justify-center p-8 gap-4">
                    <i class="pi pi-inbox text-surface-400" style="font-size: 3rem"></i>
                    <div class="text-surface-500 font-semibold">{{ t('empty') }}</div>
                </div>
            </template>
            <Column field="description" :header="t('description')"></Column>
            <Column field="paymentAt" :header="t('paymentAt')" style="min-width: 12rem">
                <template #body="slotProps">
                    {{ formatDate(slotProps.data.paymentAt, 'dd/MM/yyyy') }}
                </template>
            </Column>
            <Column field="dueDate" :header="t('dueDate')" style="min-width: 12rem">
                <template #body="slotProps">
                    {{ formatDate(slotProps.data.dueDate, 'dd/MM/yyyy') }}
                </template>
            </Column>
            <Column field="amount" :header="t('amount')" style="min-width: 12rem">
                <template #body="slotProps">
                    {{ formatCurrency(slotProps.data.amount) }}
                </template>
            </Column>
            <Column field="category" :header="t('category')"></Column>
            <Column field="account" :header="t('account')"></Column>
            <Column field="processed" :header="t('status')">
                <template #body="slotProps">
                    <Tag :value="slotProps.data.processed ? t('paid') : t('pending')"
                        :severity="slotProps.data.processed ? 'success' : 'warn'" />
                </template>
            </Column>
        </DataTable>
        <ExpenseFormDrawer v-model:visible="visibleCad" @saved="findByFilter" />
    </div>
</template>

<style scoped></style>
