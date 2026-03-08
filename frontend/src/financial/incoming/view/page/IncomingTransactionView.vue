<script lang="ts" setup>
import { PageResponse } from '@/common/model/page.response';
import { formatCurrency, formatDate, useHandlerMessage } from '@/composoable/commons';
import { FilterMatchMode } from '@primevue/core/api';
import { AxiosError } from 'axios';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { IncomingResponse } from '../../model/incoming.response';
import IncomingTransactionService from '../../service/incoming.transaction.service';
import IncomingTransactionFormDrawer from '../component/IncomingTransactionFormDrawer.vue';

const { t } = useI18n();
const pageIncomingResult = ref<PageResponse<IncomingResponse> | null>();
const handlerMessage = useHandlerMessage();
const visibleCadIncoming = ref<boolean>(false);

const filters = ref({
    'global': { value: null, matchMode: FilterMatchMode.CONTAINS },
});

onBeforeMount(async () => {
    await findByFilter();
});

const home = ref({
    icon: 'pi pi-home',
    route: '/'
});
const items = ref([
    { label: t('transaction') },
    { label: t('incoming') }
]);

const findByFilter = async () => {
    try {
        pageIncomingResult.value = await IncomingTransactionService.findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
};

const openCadIncoming = () => {
    visibleCadIncoming.value = true;
};


</script>
<template>
    <Toolbar class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl mb-4 border border-surface-200 dark:border-surface-800">
        <template #start>
            <Button :label="t('new')" icon="pi pi-plus" severity="primary" class="mr-2" @click="openCadIncoming" />
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
        <DataTable ref="dt" :value="pageIncomingResult?.content" stripedRows>
            <template #header>
                <div class="flex flex-wrap gap-2 items-center justify-between">
                    <h4 class="m-0 font-bold text-xl">{{ t('incoming') }}</h4>
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
            <Column field="description" :header="t('description')" style="min-width: 12rem" />
            <Column field="paymentAt" :header="t('paymentAt')" style="min-width: 12rem">
                <template #body="slotProps">
                    {{ formatDate(slotProps.data.paymentAt, 'dd/MM/yyyy') }}
                </template>
            </Column>
            <Column field="amount" :header="t('amount')" style="min-width: 12rem">
                <template #body="slotProps">
                    <span class="text-green-600 dark:text-green-400 font-bold">{{ formatCurrency(slotProps.data.amount) }}</span>
                </template>
            </Column>
            <Column field="category" :header="t('category')" style="min-width: 12rem" />
            <Column field="account" :header="t('account')" style="min-width: 12rem" />
        </DataTable>
        <IncomingTransactionFormDrawer v-model:visible="visibleCadIncoming" @saved="findByFilter" />
    </div>
</template>
