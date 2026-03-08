# OpusFinancial Style Guide

Este documento define os padrões visuais e de componentes para o front-end do OpusFinancial, utilizando **Vue 3**, **PrimeVue Aura** e **Tailwind CSS**.

## Princípios de Design

1. **Modernidade e Confiança**: Cores sólidas, bordas arredondadas e tipografia clara.
2. **Consistência**: Componentes semelhantes devem se comportar da mesma forma em todo o sistema.
3. **Foco no Conteúdo**: Reduzir ruídos visuais e gradientes excessivos.

## Cores (Tailwind CSS)

- **Primary**: `bg-primary`, `text-primary` (Indigo/Deep Blue).
- **Surface**: `bg-surface-0` (claro) / `bg-surface-900` (escuro).
- **Background**: `bg-surface-50` / `bg-surface-950`.
- **Text**: `text-surface-900` (títulos), `text-surface-500` (secundário).

## Componentes

### 1. Containers de Página
As telas principais devem ser envoltas em containers com bordas arredondadas e sombra suave.
- **Classes**: `bg-surface-0 dark:bg-surface-900 shadow-sm p-6 rounded-2xl border border-surface-200 dark:border-surface-800`

### 2. Toolbars e Cabeçalhos
- Use o componente `Toolbar` do PrimeVue para ações principais.
- **Estilo**: Mesmas classes do container, mas com margem inferior `mb-4`.

### 3. Tabelas (DataTable)
- Utilize `stripedRows` para facilitar a leitura.
- As colunas de "Ações" devem ter botões arredondados (`rounded`).
- **Data/Moeda**: Use os helpers de formatação (`formatDate`, `formatCurrency`).

### 4. Formulários e Drawers
- **Labels**: `font-medium text-sm mb-1 block`.
- **Inputs**: Use `fluid` para ocupar a largura total do container.
- **Botões de Ação**: Botões de salvar devem ser `severity="primary"` ou `info`, cancelar deve ser `severity="secondary"` ou `danger` (com texto de confirmação se necessário).

### 5. Diálogos de Autenticação (Login/Register)
- Centralizados na tela.
- Card com `max-w-[450px]` (Login) ou `max-w-[600px]` (Register).
- Border radius grande: `rounded-3xl`.
- Padding generoso: `p-8 md:p-12`.

## Estrutura de Código Sugerida

```vue
<template>
    <!-- 1. Toolbar de Ações -->
    <Toolbar class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl mb-4 border border-surface-200 dark:border-surface-800">
        <template #start>
            <Button :label="t('new')" icon="pi pi-plus" severity="primary" @click="openNew" />
        </template>
        <template #end>
            <Breadcrumb :home="home" :model="items" />
        </template>
    </Toolbar>

    <!-- 2. Container de Conteúdo -->
    <div class="bg-surface-0 dark:bg-surface-900 shadow-sm p-6 rounded-2xl border border-surface-200 dark:border-surface-800">
        <DataTable :value="data" stripedRows>
            <!-- Colunas -->
        </DataTable>
    </div>
</template>
```

---
*Este guia deve ser seguido para todas as novas telas e manutenções.*
