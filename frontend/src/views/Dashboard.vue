<script setup lang="ts">
import { useHandlerMessage } from '@/composoable/commons';
import StatisticWidget from '@/reporting/dashboard/components/StatisticWidget.vue';
import dashboardService from '@/reporting/dashboard/service/dashboard.service';
import { AxiosError } from 'axios';
import { onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const listBalanceSummary = ref();
const handlerMessage = useHandlerMessage();
const walletBalance = ref<number>(0);

onMounted(async () => {
    await findBalanceSummaryAccount();
});

const formatCurrency = (value: any) => {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
};

const findBalanceSummaryAccount = async () => {
    try {
        listBalanceSummary.value = await dashboardService.findAccountBalanceSummary();
        walletBalance.value = listBalanceSummary.value.find((item: any) => item.accountType === 'WALLET')?.balance || 0;

    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
}
</script>

<template>

    <div class="grid grid-cols-12 gap-8">
        <StatisticWidget :value="formatCurrency(walletBalance)" :title="t('accountBalance')" icon="pi pi-wallet"
            iconBgClass="bg-linear-to-b from-cyan-400 dark:from-cyan-300 to-cyan-600 dark:to-cyan-500" />
    </div>
</template>
