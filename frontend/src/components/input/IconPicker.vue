<script setup>
import { computed } from 'vue';

// Definição de Props
const props = defineProps({
    modelValue: {
        type: String,
        default: null
    },
    label: {
        type: String,
        default: 'Ícone'
    },
    placeholder: {
        type: String,
        default: 'Selecione uma representação'
    },
    errorMessage: {
        type: String,
        default: ''
    },
    // Permite sobrescrever a lista padrão se necessário
    customIcons: {
        type: Array,
        default: null
    }
});

// Emits para suporte ao v-model
const emit = defineEmits(['update:modelValue']);

// Proxy para manusear o v-model corretamente
const internalValue = computed({
    get: () => props.modelValue,
    set: (value) => emit('update:modelValue', value)
});

// Lista Curada de Ícones Financeiros (Padrão do Sistema)
const defaultIcons = [
    { label: 'Carteira/Dinheiro', value: 'pi pi-wallet' },
    { label: 'Banco/Instituição', value: 'pi pi-building' },
    { label: 'Cartão de Crédito', value: 'pi pi-credit-card' },
    { label: 'Investimentos/Gráfico', value: 'pi pi-chart-line' },
    { label: 'Alimentação', value: 'pi pi-shopping-cart' }, // Trocado por shopping-cart que é mais genérico no PrimeIcons
    { label: 'Transporte/Carro', value: 'pi pi-car' },
    { label: 'Moradia/Casa', value: 'pi pi-home' },
    { label: 'Saúde/Vida', value: 'pi pi-heart' },
    { label: 'Lazer/Ticket', value: 'pi pi-ticket' },
    { label: 'Educação/Livro', value: 'pi pi-book' },
    { label: 'Segurança', value: 'pi pi-shield' },
    { label: 'Configuração/Serviços', value: 'pi pi-cog' },
    { label: 'Pix/Transferência', value: 'pi pi-arrow-right-arrow-left' }
];

// Computada para decidir qual lista usar
const iconOptions = computed(() => props.customIcons || defaultIcons);
</script>

<template>
    <div class="flex flex-column gap-2">
        <Dropdown v-model="internalValue" :options="iconOptions" optionLabel="label" optionValue="value" :placeholder="placeholder" :class="{ 'p-invalid': errorMessage }" class="w-full" filter showClear>
            <template #value="slotProps">
                <div v-if="slotProps.value" class="flex align-items-center">
                    <i :class="[slotProps.value, 'mr-2 text-xl text-primary']"></i>
                    <span>{{ iconOptions.find((i) => i.value === slotProps.value)?.label }}</span>
                </div>
                <span v-else>
                    {{ slotProps.placeholder }}
                </span>
            </template>

            <template #option="slotProps">
                <div class="flex align-items-center">
                    <i :class="[slotProps.option.value, 'mr-2 text-xl text-primary']"></i>
                    <span>{{ slotProps.option.label }}</span>
                </div>
            </template>
        </Dropdown>
    </div>
</template>
