<script setup lang="ts">
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
</script>

<template>
    <div class="flex flex-col gap-4">
        <div class="flex items-center justify-between">
            <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0 m-0">Meus Cartões</h3>
            <Button as="router-link" to="/credit-card" label="Gerenciar" text size="small" />
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            <!-- Skeleton Cartão 1 (Estilo Nubank/Roxo) -->
            <div
                class="relative overflow-hidden rounded-3xl p-6 shadow-lg shadow-purple-500/20 bg-gradient-to-tr from-purple-800 to-fuchsia-600 border border-purple-500/30 flex flex-col justify-between min-h-[14rem]">
                <div class="absolute top-0 right-0 w-32 h-32 bg-white/10 rounded-full -mr-16 -mt-16 blur-2xl"></div>

                <div class="flex justify-between items-start z-10">
                    <div class="flex flex-col gap-1">
                        <Skeleton width="6rem" height="1.2rem" class="bg-white/20" />
                        <Skeleton width="4rem" height="0.8rem" class="bg-white/10" />
                    </div>
                    <Skeleton shape="circle" size="2.5rem" class="bg-white/20" />
                </div>
                <div class="flex flex-col gap-2 z-10 mt-6">
                    <span class="text-white/70 text-sm font-medium">Fatura Atual</span>
                    <Skeleton width="10rem" height="2rem" class="bg-white/20" />
                </div>

                <div class="flex flex-col gap-2 z-10 mt-6">
                    <div class="flex justify-between text-white/70 text-xs">
                        <Skeleton width="5rem" height="0.6rem" class="bg-white/20" />
                        <Skeleton width="3rem" height="0.6rem" class="bg-white/20" />
                    </div>
                    <Skeleton width="100%" height="6px" class="bg-white/10" />
                </div>
            </div>
            <!-- Skeleton Cartão 2 (Estilo Black/Cinza) -->
            <div
                class="relative overflow-hidden rounded-3xl p-6 shadow-lg shadow-gray-900/40 bg-gradient-to-br from-gray-800 to-gray-950 border border-surface-700 flex flex-col justify-between min-h-[14rem]">
                <div class="absolute bottom-0 left-0 w-40 h-40 bg-surface-500/10 rounded-full -ml-20 -mb-20 blur-3xl">
                </div>

                <div class="flex justify-between items-start z-10">
                    <div class="flex flex-col gap-1">
                        <Skeleton width="5rem" height="1.2rem" class="bg-surface-600" />
                        <Skeleton width="7rem" height="0.8rem" class="bg-surface-700" />
                    </div>
                    <Skeleton shape="circle" size="2.5rem" class="bg-surface-600" />
                </div>
                <div class="flex flex-col gap-2 z-10 mt-6">
                    <span class="text-surface-400 text-sm font-medium">Fatura Atual</span>
                    <Skeleton width="9rem" height="2rem" class="bg-surface-600" />
                </div>

                <div class="flex flex-col gap-2 z-10 mt-6">
                    <div class="flex justify-between text-surface-400 text-xs">
                        <Skeleton width="6rem" height="0.6rem" class="bg-surface-600" />
                        <Skeleton width="4rem" height="0.6rem" class="bg-surface-600" />
                    </div>
                    <Skeleton width="100%" height="6px" class="bg-surface-700" />
                </div>
            </div>

            <!-- Esqueleto para "Adicionar Cartão" -->
            <div
                class="rounded-3xl p-6 border-2 border-dashed border-surface-200 dark:border-surface-700 hover:border-primary transition-colors flex flex-col items-center justify-center min-h-[14rem] cursor-pointer group bg-surface-0 dark:bg-surface-900/50">
                <div
                    class="w-16 h-16 rounded-full bg-surface-50 dark:bg-surface-800 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
                    <i
                        class="pi pi-plus text-2xl text-surface-400 dark:text-surface-500 group-hover:text-primary transition-colors"></i>
                </div>
                <span class="font-medium text-surface-600 dark:text-surface-300">Novo Cartão</span>
            </div>
        </div>
    </div>
</template>
