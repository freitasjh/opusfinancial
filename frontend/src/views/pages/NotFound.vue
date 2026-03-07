<script setup>
import FloatingConfigurator from '@/components/FloatingConfigurator.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
</script>

<template>
    <FloatingConfigurator />
    <div class="flex items-center justify-center min-h-screen overflow-hidden bg-surface-50 dark:bg-surface-950">
        <div class="flex flex-col items-center justify-center w-full max-w-2xl px-4">
            <div
                class="bg-surface-0 dark:bg-surface-900 shadow-sm rounded-2xl p-8 sm:p-12 flex flex-col items-center text-center w-full">
                <div
                    class="flex items-center justify-center bg-primary-100 dark:bg-primary-900/30 rounded-full w-24 h-24 mb-6">
                    <i class="pi pi-exclamation-triangle text-primary text-5xl"></i>
                </div>
                <span class="text-primary font-bold text-6xl mb-2">404</span>
                <h1 class="text-surface-900 dark:text-surface-0 font-bold text-3xl mb-4">{{ t('pageNotFound') }}</h1>
                <div class="text-surface-600 dark:text-surface-200 mb-8 max-w-md">
                    {{ t('pageNotFoundDesc') }}
                </div>

                <div class="flex flex-col w-full gap-4 max-w-sm mb-8">
                    <router-link to="/"
                        class="w-full flex items-center p-4 border border-surface-200 dark:border-surface-700 rounded-xl hover:bg-surface-50 dark:hover:bg-surface-800 transition-colors">
                        <span
                            class="flex justify-center items-center bg-primary-50 dark:bg-primary-900/20 text-primary rounded-lg w-12 h-12 shrink-0">
                            <i class="pi pi-question-circle text-xl"></i>
                        </span>
                        <span class="ml-4 flex flex-col text-left">
                            <span class="text-surface-900 dark:text-surface-0 font-medium">{{ t('supportCenter')
                                }}</span>
                            <span class="text-surface-500 dark:text-surface-400 text-sm mt-1">{{ t('supportCenterDesc')
                                }}</span>
                        </span>
                    </router-link>

                    <router-link to="/"
                        class="w-full flex items-center p-4 border border-surface-200 dark:border-surface-700 rounded-xl hover:bg-surface-50 dark:hover:bg-surface-800 transition-colors">
                        <span
                            class="flex justify-center items-center bg-primary-50 dark:bg-primary-900/20 text-primary rounded-lg w-12 h-12 shrink-0">
                            <i class="pi pi-comments text-xl"></i>
                        </span>
                        <span class="ml-4 flex flex-col text-left">
                            <span class="text-surface-900 dark:text-surface-0 font-medium">{{ t('contactSupport')
                                }}</span>
                            <span class="text-surface-500 dark:text-surface-400 text-sm mt-1">{{ t('contactSupportDesc')
                                }}</span>
                        </span>
                    </router-link>
                </div>

                <Button as="router-link" :label="t('backToHome')" to="/" icon="pi pi-home"
                    class="w-full sm:w-auto px-8" />
            </div>
        </div>
    </div>
</template>
