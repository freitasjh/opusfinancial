<script setup lang="ts">
import { formatCurrency, useHandlerMessage } from '@/composoable/commons';
import StatisticWidget from '@/reporting/dashboard/components/StatisticWidget.vue';
import { useDashboardStore } from '@/reporting/dashboard/store/dashboard.store';
import { AxiosError } from 'axios';
import { onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import DashboardHeader from '@/reporting/dashboard/components/DashboardHeader.vue';
import LastExpensesWidget from '@/reporting/dashboard/widget/LastExpensesWidget.vue';
import LastIncomingWidget from '@/reporting/dashboard/widget/LastIncomingWidget.vue';
import QuickActionsWidget from '@/reporting/dashboard/widget/QuickActionsWidget.vue';

const { t } = useI18n();
const dashboardStore = useDashboardStore();
const handlerMessage = useHandlerMessage();

onMounted(async () => {
    try {
        await dashboardStore.fetchBalanceSummary();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
});


const getIconByAccountType = (type: string) => {
    switch (type) {
        case 'WALLET': return 'pi pi-wallet';
        case 'CHECKING': return 'pi pi-credit-card';
        case 'SAVING': return 'pi pi-building-columns';
        case 'INVESTMENT': return 'pi pi-chart-line';
        default: return 'pi pi-money-bill';
    }
}

const getColorByAccountType = (type: string) => {
    switch (type) {
        case 'WALLET': return 'bg-linear-to-b from-indigo-500 to-indigo-700 shadow-lg shadow-indigo-500/30';
        case 'CHECKING': return 'bg-linear-to-b from-emerald-500 to-emerald-700 shadow-lg shadow-emerald-500/30';
        case 'SAVING': return 'bg-linear-to-b from-amber-500 to-amber-700 shadow-lg shadow-amber-500/30';
        case 'INVESTMENT': return 'bg-linear-to-b from-purple-500 to-purple-700 shadow-lg shadow-purple-500/30';
        default: return 'bg-linear-to-b from-gray-500 to-gray-700 shadow-lg shadow-gray-500/30';
    }
}

const carouselResponsiveOptions = ref([
    {
        breakpoint: '1400px',
        numVisible: 3,
        numScroll: 1
    },
    {
        breakpoint: '1024px',
        numVisible: 2,
        numScroll: 1
    },
    {
        breakpoint: '768px',
        numVisible: 1,
        numScroll: 1
    }
]);
</script>

<template>
    <div class="flex flex-col gap-6 w-full fade-in pb-12">
        <DashboardHeader :totalBalance="dashboardStore.totalBalance" />

        <!-- Widgets de Contas com Carrossel -->
        <div>
            <Carousel v-if="dashboardStore.balanceSummaries.length > 0" :value="dashboardStore.balanceSummaries"
                :numVisible="4" :numScroll="1" :responsiveOptions="carouselResponsiveOptions" circular
                :autoplayInterval="5000">
                <template #item="slotProps">
                    <div class="px-3">
                        <StatisticWidget :value="formatCurrency(slotProps.data.balance)"
                            :title="t(slotProps.data.accountType)"
                            :icon="getIconByAccountType(slotProps.data.accountType)"
                            :iconBgClass="getColorByAccountType(slotProps.data.accountType)" />
                    </div>
                </template>
            </Carousel>

            <!-- Fallback if empty -->
            <div v-if="!dashboardStore.balanceSummaries.length && !dashboardStore.loading"
                class="bg-surface-0 dark:bg-surface-900 border border-surface-200 dark:border-surface-800 border-dashed rounded-3xl p-12 flex flex-col items-center justify-center text-center gap-4">
                <div
                    class="w-16 h-16 bg-surface-100 dark:bg-surface-800 rounded-full flex items-center justify-center text-surface-400">
                    <i class="pi pi-wallet text-2xl"></i>
                </div>
                <div class="flex flex-col gap-1">
                    <span class="font-bold text-surface-900 dark:text-surface-0 text-xl">{{ t('noAccountsFound')
                        }}</span>
                    <span class="text-surface-500 dark:text-surface-400">{{ t('registerAccountToSeeSummary') }}</span>
                </div>
                <Button as="router-link" to="/account" :label="t('registerAccount')" icon="pi pi-plus" class="mt-4"
                    severity="primary" rounded />
            </div>
        </div>

        <QuickActionsWidget />

        <!-- <CreditCardSkeleton /> -->

        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 pb-6">
            <LastIncomingWidget />
            <LastExpensesWidget />
        </div>
    </div>
</template>
