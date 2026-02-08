<script setup lang="ts">
import { PageResponse } from '@/common/model/page.response';

import { useHandlerMessage } from '@/composoable/commons';
import { Bank } from '@/financial/bank/model/bank.model';
import bankService from '@/financial/bank/service/bank.service';
import { AxiosError } from 'axios';
import { Select } from 'primevue';
import { onBeforeMount, ref } from 'vue';
import { Account } from '../../model/account.model';
import { AccountResponse } from '../../model/account.response';
import AccountService from '../../service/account.service';

const handlerMessage = useHandlerMessage();

const loadingTable = ref<boolean>(false);
const pageAccountResult = ref<PageResponse<AccountResponse> | null>();
const accountSelected = ref<AccountResponse | null>();
const pageBankResult = ref<PageResponse<Bank> | null>();
const visibleDrawerCadCategory = ref<boolean>(false);
const account = ref<Account>({} as Account);
const bankSelected = ref<Bank | null>();
const filterTextBank = ref<string>('');

onBeforeMount(async () => {
    await findByFilter();
});

const findByFilter = async () => {
    try {
        loadingTable.value = true;
        pageAccountResult.value = await AccountService.findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loadingTable.value = false;
    }
};

const findBankByFilter = async () => {
    try {
        pageBankResult.value = await bankService.findByFilter();
    } catch (error: any) {
        handlerMessage.error(error);
    }
};

const openDrawerCad = async () => {
    visibleDrawerCadCategory.value = true;
};

const onFilter = async (event: any) => {
    pageBankResult.value = await bankService.findByFilter(0, event.value);
};

const findById = async () => {};
</script>
<template>
    <div>
        <div class="card">
            <Toolbar class="mb-6">
                <template #start>
                    <Button label="New" icon="pi pi-plus" severity="secondary" class="mr-2" @click="openDrawerCad" />
                </template>
            </Toolbar>
            <DataTable ref="dt" v-model:selection="accountSelected" :value="pageAccountResult?.content" dataKey="id" :paginator="true" :rows="30">
                <Column field="accountName" header="Descricao" sortable style="min-width: 12rem" />
            </DataTable>
            <Drawer v-model:visible="visibleDrawerCadCategory" :dismissable="false" position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Nome</label>
                    <InputText id="account-name" v-model="account.accountName" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Saldo</label>
                    <InputNumber id="account-name" v-model="account.balance" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Saldo</label>
                    <InputNumber id="account-name" v-model="account.balance" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-2 mt-2">
                    <label>Banco</label>
                    <Select :options="pageBankResult?.content" fluid placeholder="Selecione um banco" filter optionLabel="name" v-model="bankSelected" @filter="onFilter" showClear @before-show="findBankByFilter"></Select>
                </div>
            </Drawer>
        </div>
    </div>
</template>
