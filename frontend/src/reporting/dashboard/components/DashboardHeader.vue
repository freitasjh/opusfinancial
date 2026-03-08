<script setup lang="ts">
import { formatCurrency } from '@/composoable/commons';
import { useAuthStore } from '@/identity/store/auth.store';
import { ref } from 'vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const authStore = useAuthStore();

const props = defineProps<{
    totalBalance: number;
}>();

// Data do filtro iniciando com o mês atual (Year/Month component)
const filterDate = ref<Date>(new Date());
</script>

<template>
    <div
        class="bg-surface-0 dark:bg-surface-900 rounded-3xl p-8 border border-surface-200 dark:border-surface-800 shadow-sm flex flex-col md:flex-row items-center justify-between gap-6 relative overflow-hidden">
        <div
            class="absolute -right-20 -top-20 w-64 h-64 bg-primary-100 dark:bg-primary-900/20 rounded-full blur-3xl opacity-50 z-0 text-transparent">
        </div>

        <div class="flex flex-col gap-6 z-10 w-full flex-1">
            <div class="flex flex-col gap-1">
                <h1 class="text-surface-900 dark:text-surface-0 text-3xl font-bold m-0 flex items-center gap-3">
                    {{ t('welcome') }}, {{ authStore.userName }} <span class="text-3xl">👋</span>
                </h1>
                <p class="text-surface-500 dark:text-surface-400 font-medium m-0 text-lg">
                    {{ t('financeSummaryToday') }}
                </p>
            </div>

            <div class="flex items-center gap-3">
                <span class="text-surface-700 dark:text-surface-200 font-medium flex items-center">
                    <i class="pi pi-calendar text-primary mr-2 text-xl"></i>
                    Mês de referência:
                </span>
                <DatePicker v-model="filterDate" view="month" dateFormat="mm/yy"
                    class="w-40 shadow-sm [&>input]:font-bold [&>input]:text-center [&>input]:text-primary [&>input]:text-lg" />
            </div>
        </div>

        <div
            class="z-10 bg-surface-50 dark:bg-surface-950 p-6 rounded-2xl border border-surface-200 dark:border-surface-800 flex items-center gap-6 min-w-max shadow-sm w-full md:w-auto mt-2 md:mt-0">
            <div
                class="w-14 h-14 bg-primary-100 dark:bg-primary-900/30 rounded-full flex items-center justify-center text-primary shrink-0">
                <i class="pi pi-chart-pie text-2xl font-bold"></i>
            </div>
            <div class="flex flex-col">
                <span class="text-surface-500 dark:text-surface-400 font-medium whitespace-nowrap">{{
                    t('consolidatedBalance') }}</span>
                <span class="text-3xl font-bold text-surface-900 dark:text-surface-0">{{ formatCurrency(totalBalance)
                }}</span>
            </div>
        </div>
    </div>
</template>
