<script setup lang="ts">
import { useI18n } from 'vue-i18n';

interface Props {
    visible: boolean;
    header: string;
    loading?: boolean;
    saveLabel?: string;
    cancelLabel?: string;
}

const { t } = useI18n();

withDefaults(defineProps<Props>(), {
    loading: false,
    saveLabel: '',
    cancelLabel: ''
});

const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void;
    (e: 'save'): void;
    (e: 'cancel'): void;
}>();

const handleCancel = () => {
    emit('cancel');
    emit('update:visible', false);
};
</script>

<template>
    <Drawer :visible="visible" @update:visible="emit('update:visible', $event)" :header="header" position="right"
        class="!w-full md:!w-80 lg:!w-[30rem]" :dismissable="false">
        <div class="flex flex-col h-full relative">
            <!-- Conteúdo com Scroll -->
            <div class="flex-1 overflow-y-auto px-1 pb-20">
                <slot></slot>
            </div>

            <!-- Botões Fixos no Rodapé -->
            <div class="absolute bottom-0 left-0 w-full flex flex-col gap-2 pt-4 bg-surface-0 dark:bg-surface-900 border-t border-surface-200 dark:border-surface-800">
                <Button 
                    type="submit" 
                    severity="primary" 
                    :label="saveLabel || t('save')" 
                    @click="emit('save')" 
                    :loading="loading" 
                    class="w-full p-4 font-bold rounded-xl" 
                />
                <Button 
                    type="button" 
                    severity="danger" 
                    text 
                    :label="cancelLabel || t('cancel')" 
                    @click="handleCancel" 
                    class="w-full font-bold rounded-xl" 
                />
            </div>
        </div>
    </Drawer>
</template>

<style scoped>
/* Ajuste para garantir que o Drawer do PrimeVue não corte o conteúdo fixo se necessário */
:deep(.p-drawer-content) {
    display: flex;
    flex-direction: column;
    height: 100%;
    padding-bottom: 0;
}
</style>
