<script setup lang="ts">
import FloatingConfigurator from '@/components/FloatingConfigurator.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
</script>

<template>
    <FloatingConfigurator />
    <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] p-4 overflow-hidden">
        <div class="w-full max-w-[600px]">
            <div class="bg-surface-0 dark:bg-surface-900 shadow-xl rounded-3xl p-8 md:p-12 border border-surface-200 dark:border-surface-800 text-center flex flex-col items-center">
                <div class="flex justify-center mb-6">
                    <div class="w-20 h-24 bg-primary-100 dark:bg-primary-900/30 rounded-2xl flex items-center justify-center text-primary">
                        <i class="pi pi-exclamation-triangle text-5xl"></i>
                    </div>
                </div>
                <span class="text-primary font-bold text-6xl mb-2">404</span>
                <h1 class="text-surface-900 dark:text-surface-0 font-bold text-3xl mb-3">{{ t('pageNotFound') }}</h1>
                <p class="text-surface-500 dark:text-surface-400 font-medium mb-8">{{ t('pageNotFoundDesc') }}</p>

                <div class="flex flex-col w-full gap-4 max-w-md mb-8">
                    <router-link to="/"
                        class="w-full flex items-center p-4 border border-surface-200 dark:border-surface-700 rounded-2xl hover:bg-surface-50 dark:hover:bg-surface-800 transition-all group">
                        <span
                            class="flex justify-center items-center bg-primary-50 dark:bg-primary-900/20 text-primary rounded-xl w-12 h-12 shrink-0 group-hover:scale-110 transition-transform">
                            <i class="pi pi-question-circle text-xl"></i>
                        </span>
                        <span class="ml-4 flex flex-col text-left">
                            <span class="text-surface-900 dark:text-surface-0 font-bold">{{ t('supportCenter') }}</span>
                            <span class="text-surface-500 dark:text-surface-400 text-sm mt-1">{{ t('supportCenterDesc') }}</span>
                        </span>
                    </router-link>

                    <router-link to="/"
                        class="w-full flex items-center p-4 border border-surface-200 dark:border-surface-700 rounded-2xl hover:bg-surface-50 dark:hover:bg-surface-800 transition-all group">
                        <span
                            class="flex justify-center items-center bg-primary-50 dark:bg-primary-900/20 text-primary rounded-xl w-12 h-12 shrink-0 group-hover:scale-110 transition-transform">
                            <i class="pi pi-comments text-xl"></i>
                        </span>
                        <span class="ml-4 flex flex-col text-left">
                            <span class="text-surface-900 dark:text-surface-0 font-bold">{{ t('contactSupport') }}</span>
                            <span class="text-surface-500 dark:text-surface-400 text-sm mt-1">{{ t('contactSupportDesc') }}</span>
                        </span>
                    </router-link>
                </div>

                <Button as="router-link" :label="t('backToHome')" to="/" icon="pi pi-home"
                    class="w-full sm:w-auto px-8 p-4 font-bold rounded-2xl" />
            </div>
        </div>
    </div>
</template>
