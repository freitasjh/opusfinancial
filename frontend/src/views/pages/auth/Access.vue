<script setup lang="ts">
import FloatingConfigurator from '@/components/FloatingConfigurator.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
</script>

<template>
    <FloatingConfigurator />
    <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] p-4 overflow-hidden">
        <div class="w-full max-w-[500px]">
            <div class="bg-surface-0 dark:bg-surface-900 shadow-xl rounded-3xl p-8 md:p-12 border border-surface-200 dark:border-surface-800 text-center">
                <div class="flex justify-center mb-6">
                    <div class="w-20 h-24 bg-orange-100 dark:bg-orange-900/30 rounded-2xl flex items-center justify-center">
                        <i class="pi pi-lock text-orange-500 text-5xl"></i>
                    </div>
                </div>
                <h1 class="text-surface-900 dark:text-surface-0 font-bold text-3xl mb-3">{{ t('accessDenied') }}</h1>
                <p class="text-surface-500 dark:text-surface-400 font-medium mb-8">{{ t('accessDeniedDesc') }}</p>
                
                <img src="/demo/images/access/asset-access.svg" alt="Access denied" class="w-full mb-8" />
                
                <Button as="router-link" :label="t('goToDashboard')" to="/" severity="warn" class="w-full p-4 font-bold" />
            </div>
        </div>
    </div>
</template>
