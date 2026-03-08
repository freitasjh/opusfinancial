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
                    <div class="w-20 h-24 bg-pink-100 dark:bg-pink-900/30 rounded-2xl flex items-center justify-center">
                        <i class="pi pi-exclamation-circle text-pink-500 text-5xl"></i>
                    </div>
                </div>
                <h1 class="text-surface-900 dark:text-surface-0 font-bold text-3xl mb-3">{{ t('errorOccurred') }}</h1>
                <p class="text-surface-500 dark:text-surface-400 font-medium mb-8">{{ t('errorOccurredDesc') }}</p>
                
                <img src="/demo/images/error/asset-error.svg" alt="Error" class="w-full mb-8" />
                
                <Button as="router-link" :label="t('goToDashboard')" to="/" severity="danger" class="w-full p-4 font-bold" />
            </div>
        </div>
    </div>
</template>
