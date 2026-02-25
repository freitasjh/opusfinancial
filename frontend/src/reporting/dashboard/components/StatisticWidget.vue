<script setup lang="ts">
export interface StatisticWidgetProps {
    title?: string;
    value?: string | number;
    icon?: string;
    iconBgClass?: string;
    descriptionHighlight?: string;
    description?: string;
}

withDefaults(defineProps<StatisticWidgetProps>(), {
    title: 'Messages',
    value: 152,
    icon: 'pi pi-envelope',
    iconBgClass: 'bg-linear-to-b from-cyan-400 dark:from-cyan-300 to-cyan-600 dark:to-cyan-500',
    descriptionHighlight: '',
    description: ''
});
</script>

<template>
    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
        <div class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl">
            <div class="flex justify-between gap-4">
                <div class="flex flex-col gap-2">
                    <span class="text-surface-700 dark:text-surface-300 font-normal leading-tight">{{ title }}</span>
                    <div class="text-surface-900 dark:text-surface-0 font-semibold text-2xl! leading-tight!">{{ value }}
                    </div>
                </div>
                <div class="flex items-center justify-center rounded-lg w-10 h-10" :class="iconBgClass">
                    <i class="text-surface-0 dark:text-surface-900 text-xl! leading-none!" :class="icon" />
                </div>
            </div>
            <div class="mt-4" v-if="descriptionHighlight || description">
                <span v-if="descriptionHighlight"
                    class="text-surface-600 dark:text-surface-300 font-medium leading-tight pr-1">{{
                        descriptionHighlight }}</span>
                <span v-if="description" class="text-surface-500 dark:text-surface-300 leading-tight">{{ description
                }}</span>
            </div>
        </div>
    </div>
</template>
