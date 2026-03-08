import { defineStore } from 'pinia';
import { computed, ref } from 'vue';
import type { AccountBalanceSummary } from '../model/account.balance.summary';
import type { ExpenseReport } from '../model/expense.report';
import type { IncomingReport } from '../model/incoming.report';
import dashboardService from '../service/dashboard.service';

export const useDashboardStore = defineStore('dashboard', () => {
    const balanceSummaries = ref<AccountBalanceSummary[]>([]);
    const lastExpenses = ref<ExpenseReport[]>([]);
    const lastIncomings = ref<IncomingReport[]>([]);

    const loading = ref(false);
    const loadingExpenses = ref(false);
    const loadingIncomings = ref(false);

    const walletBalance = computed(() => {
        return balanceSummaries.value.find((item) => item.accountType === 'WALLET')?.balance || 0;
    });

    const investmentBalance = computed(() => {
        return balanceSummaries.value.find((item) => item.accountType === 'INVESTMENT')?.balance || 0;
    });

    const totalBalance = computed(() => {
        return balanceSummaries.value.reduce((acc, item) => acc + item.balance, 0);
    });

    async function fetchBalanceSummary() {
        try {
            loading.value = true;
            balanceSummaries.value = await dashboardService.findAccountBalanceSummary();
        } finally {
            loading.value = false;
        }
    }

    async function fetchLastExpenses() {
        try {
            loadingExpenses.value = true;
            lastExpenses.value = await dashboardService.findLastExpense();
        } finally {
            loadingExpenses.value = false;
        }
    }

    async function fetchLastIncomings() {
        try {
            loadingIncomings.value = true;
            lastIncomings.value = await dashboardService.findLastIncoming();
        } finally {
            loadingIncomings.value = false;
        }
    }

    return {
        balanceSummaries,
        lastExpenses,
        lastIncomings,
        loading,
        loadingExpenses,
        loadingIncomings,
        walletBalance,
        investmentBalance,
        totalBalance,
        fetchBalanceSummary,
        fetchLastExpenses,
        fetchLastIncomings
    };
});
