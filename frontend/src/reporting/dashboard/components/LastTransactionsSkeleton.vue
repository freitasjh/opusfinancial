<script setup lang="ts">
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
</script>

<template>
    <div
        class="bg-surface-0 dark:bg-surface-900 p-6 rounded-3xl border border-surface-200 dark:border-surface-800 shadow-sm flex flex-col gap-4">
        <div class="flex items-center justify-between">
            <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0 m-0 flex items-center gap-2">
                <i class="pi pi-arrow-up-circle text-green-500"></i>
                Últimas Receitas
            </h3>
            <Button as="router-link" to="/incoming" label="Ver todas" text size="small" />
        </div>
        <div class="flex flex-col gap-4 mt-2">
            <div v-for="i in 3" :key="'inc-' + i"
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
    </div>
</template>
