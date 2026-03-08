<script setup lang="ts">
import { formatCurrency, formatDate } from '@/composoable/commons';
import { onMounted } from 'vue';
import { useDashboardStore } from '../store/dashboard.store';

const dashboardStore = useDashboardStore();

onMounted(async () => {
    await dashboardStore.fetchLastExpenses();
});

const getCategoryIcon = (categoryName: string) => {
    if (!categoryName) return 'pi-receipt';

    const lowerName = categoryName.toLowerCase();
    if (lowerName.includes('alimentação') || lowerName.includes('food') || lowerName.includes('restaurante')) return 'pi-shop';
    if (lowerName.includes('transporte') || lowerName.includes('carro') || lowerName.includes('uber')) return 'pi-car';
    if (lowerName.includes('saúde') || lowerName.includes('health') || lowerName.includes('farmácia')) return 'pi-heart-fill';
    if (lowerName.includes('lazer') || lowerName.includes('entretenimento')) return 'pi-ticket';
    if (lowerName.includes('moradia') || lowerName.includes('casa')) return 'pi-home';
    if (lowerName.includes('educação') || lowerName.includes('escola')) return 'pi-book';

    return 'pi-receipt';
};
</script>

<template>
    <div
        class="bg-surface-0 dark:bg-surface-900 p-6 rounded-3xl border border-surface-200 dark:border-surface-800 shadow-sm flex flex-col gap-4">
        <div class="flex items-center justify-between">
            <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0 m-0 flex items-center gap-2">
                <i class="pi pi-arrow-down-circle text-red-500"></i>
                Últimas Despesas
            </h3>
            <Button as="router-link" to="/expense" label="Ver todas" text severity="danger" size="small" />
        </div>

        <!-- Loading Skeleton -->
        <div v-if="dashboardStore.loadingExpenses" class="flex flex-col gap-4 mt-2">
            <div v-for="i in 3" :key="'exp-skel-' + i"
                class="flex items-center justify-between p-3 rounded-xl bg-surface-50 dark:bg-surface-950/50 border border-surface-100 dark:border-surface-800">
                <div class="flex items-center gap-3 w-full">
                    <Skeleton shape="circle" size="3rem" class="shrink-0" />
                    <div class="flex flex-col gap-2 w-full max-w-[12rem]">
                        <Skeleton width="100%" height="1rem" />
                        <Skeleton width="60%" height="0.8rem" />
                    </div>
                </div>
                <div class="flex flex-col items-end gap-2 shrink-0">
                    <Skeleton width="4.5rem" height="1.2rem" />
                    <Skeleton width="3.5rem" height="0.8rem" />
                </div>
            </div>
        </div>

        <!-- Actual Data List -->
        <div v-else-if="dashboardStore.lastExpenses.length > 0"
            class="flex flex-col gap-4 mt-2 max-h-[24rem] overflow-y-auto pr-2 custom-scrollbar">
            <div v-for="expense in dashboardStore.lastExpenses" :key="expense.id"
                class="flex items-center justify-between p-3 rounded-xl bg-surface-50 dark:bg-surface-950/50 border border-surface-100 dark:border-surface-800 hover:shadow-sm transition-shadow">

                <div class="flex items-center gap-4 truncate">
                    <div
                        class="w-12 h-12 rounded-full bg-red-100 dark:bg-red-900/30 text-red-600 dark:text-red-400 flex items-center justify-center shrink-0">
                        <i :class="['pi text-xl', getCategoryIcon(expense.category)]"></i>
                    </div>
                    <div class="flex flex-col truncate">
                        <span class="font-bold text-surface-900 dark:text-surface-0 truncate"
                            :title="expense.description">{{ expense.description }}</span>
                        <div
                            class="flex items-center gap-2 text-sm text-surface-500 dark:text-surface-400 mt-1 truncate">
                            <span class="truncate">{{ expense.category || 'Sem categoria' }}</span>
                            <i class="pi pi-circle-fill shrink-0" style="font-size: 0.3rem"></i>
                            <span class="truncate">{{ expense.account || 'Sem conta' }}</span>
                        </div>
                    </div>
                </div>

                <div class="flex flex-col items-end shrink-0 ml-4">
                    <span class="font-bold text-red-600 dark:text-red-400">{{ formatCurrency(expense.amount) }}</span>
                    <span class="text-sm text-surface-500 dark:text-surface-400 mt-1">{{ formatDate(expense.dueDate)
                        }}</span>
                </div>
            </div>
        </div>

        <!-- Empty State -->
        <div v-else
            class="flex flex-col items-center justify-center p-8 text-center gap-3 text-surface-500 dark:text-surface-400">
            <div
                class="w-16 h-16 rounded-full bg-surface-100 dark:bg-surface-800 flex items-center justify-center mb-2">
                <i class="pi pi-check text-2xl text-surface-400 dark:text-surface-500"></i>
            </div>
            <span class="font-medium">Nenhuma despesa recente encontrada.</span>
        </div>
    </div>
</template>
