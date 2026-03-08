<script setup lang="ts">
import { ref } from 'vue';
import { useI18n } from 'vue-i18n';

// Importando os componentes de formulários nos Drawers
import CategoryFormDrawer from '@/financial/category/views/components/CategoryFormDrawer.vue';
import ExpenseFormDrawer from '@/financial/expense/view/component/ExpenseFormDrawer.vue';
import IncomingTransactionFormDrawer from '@/financial/incoming/view/component/IncomingTransactionFormDrawer.vue';

const { t } = useI18n();

// Controle de visibilidade dos Drawers
const showIncomingDrawer = ref(false);
const showExpenseDrawer = ref(false);
const showCategoryDrawer = ref(false);

const handleIncomingSaved = () => {
    // Ação pós-salvamento: recarregar dashboard etc
    showIncomingDrawer.value = false;
};

const handleExpenseSaved = () => {
    showExpenseDrawer.value = false;
};

const handleCategorySaved = () => {
    showCategoryDrawer.value = false;
};
</script>

<template>
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div class="col-span-1 lg:col-span-3">
            <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0 mb-4">{{ t('quickActions') }}</h3>
        </div>

        <div @click="showIncomingDrawer = true"
            class="bg-surface-0 dark:bg-surface-900 p-6 rounded-2xl border border-surface-200 dark:border-surface-800 hover:border-green-500 hover:shadow-md transition-all group flex items-start gap-4 cursor-pointer">
            <div
                class="w-12 h-12 bg-green-100 dark:bg-green-900/30 text-green-600 dark:text-green-400 rounded-xl flex items-center justify-center shrink-0 group-hover:scale-110 transition-transform">
                <i class="pi pi-arrow-up text-xl font-bold"></i>
            </div>
            <div class="flex flex-col gap-1">
                <span class="font-bold text-surface-900 dark:text-surface-0">{{ t('newIncoming') }}</span>
                <span class="text-sm text-surface-500 dark:text-surface-400">{{ t('newIncomingDesc') }}</span>
            </div>
        </div>

        <div @click="showExpenseDrawer = true"
            class="bg-surface-0 dark:bg-surface-900 p-6 rounded-2xl border border-surface-200 dark:border-surface-800 hover:border-red-500 hover:shadow-md transition-all group flex items-start gap-4 cursor-pointer">
            <div
                class="w-12 h-12 bg-red-100 dark:bg-red-900/30 text-red-600 dark:text-red-400 rounded-xl flex items-center justify-center shrink-0 group-hover:scale-110 transition-transform">
                <i class="pi pi-arrow-down text-xl font-bold"></i>
            </div>
            <div class="flex flex-col gap-1">
                <span class="font-bold text-surface-900 dark:text-surface-0">{{ t('newExpense') }}</span>
                <span class="text-sm text-surface-500 dark:text-surface-400">{{ t('newExpenseDesc') }}</span>
            </div>
        </div>

        <div @click="showCategoryDrawer = true"
            class="bg-surface-0 dark:bg-surface-900 p-6 rounded-2xl border border-surface-200 dark:border-surface-800 hover:border-primary hover:shadow-md transition-all group flex items-start gap-4 cursor-pointer">
            <div
                class="w-12 h-12 bg-primary-100 dark:bg-primary-900/30 text-primary rounded-xl flex items-center justify-center shrink-0 group-hover:scale-110 transition-transform">
                <i class="pi pi-tags text-xl"></i>
            </div>
            <div class="flex flex-col gap-1">
                <span class="font-bold text-surface-900 dark:text-surface-0">{{ t('category') }}</span>
                <span class="text-sm text-surface-500 dark:text-surface-400">Gerenciar categorias</span>
            </div>
        </div>

        <!-- Drawers Modals incorporados ao Dashboard -->
        <IncomingTransactionFormDrawer v-model:visible="showIncomingDrawer" @saved="handleIncomingSaved" />
        <ExpenseFormDrawer v-model:visible="showExpenseDrawer" @saved="handleExpenseSaved" />
        <CategoryFormDrawer v-model:visible="showCategoryDrawer" @saved="handleCategorySaved" />
    </div>
</template>
