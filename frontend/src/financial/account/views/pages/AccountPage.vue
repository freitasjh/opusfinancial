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
import { AccountType } from '../../model/account.type';
import AccountService from '../../service/account.service';

const handlerMessage = useHandlerMessage();

const loadingTable = ref<boolean>(false);
const pageAccountResult = ref<PageResponse<AccountResponse> | null>();
const accountSelected = ref<AccountResponse | any>();
const listBank = ref<Bank[]>([]);
const visibleDrawerCadAccount = ref<boolean>(false);
const account = ref<Account>({} as Account);
const bankSelected = ref<Bank | any>();

onBeforeMount(async () => {
    await findByFilter();
});

const typeOptions = ref([
    { label: 'Carteira', value: AccountType.WALLET },
    { label: 'Investimento', value: AccountType.INVESTMENT },
    { label: 'Poupança', value: AccountType.SAVING },
    { label: 'Conta Corrente', value: AccountType.CHECKING }
]);

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

const findAllBank = async () => {
    try {
        listBank.value = await bankService.findAll();
    } catch (error: any) {
        handlerMessage.error(error);
    }
};

const openDrawerCad = async () => {
    visibleDrawerCadAccount.value = true;
    account.value = {} as Account;
    bankSelected.value = {} as Bank;
};

const findById = async (id: string) => {
    try {
        account.value = await AccountService.findById(id);
        await findAllBank();
        bankSelected.value = listBank.value.find((item) => item.id === account.value.bankId);
        visibleDrawerCadAccount.value = true;
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
};

const save = async () => {
    try {
        account.value.bankId = bankSelected.value?.id;

        await AccountService.save(account.value);
        handlerMessage.toastSuccess('Conta salva com sucesso');
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    }
};
</script>
<template>
    <div>
        <div class="card">
            <Toolbar class="mb-6">
                <template #start>
                    <Button label="New" icon="pi pi-plus" severity="info" class="mr-2" @click="openDrawerCad" />
                </template>
            </Toolbar>
            <DataTable ref="dt" v-model:selection="accountSelected" :value="pageAccountResult?.content" dataKey="id" :paginator="true" :rows="30">
                <Column field="accountName" header="Descricao" sortable style="min-width: 12rem" />
                <Column field="bank" header="Banco" sortable style="min-width: 12rem" />
                <Column field="accountType" header="Tipo Conta" sortable style="min-width: 12rem" />
                <Column style="width: 10rem" header="Ações">
                    <template #body="slotProps">
                        <div class="flex flex-wrap gap-2">
                            <Button type="button" icon="pi pi-pencil" rounded severity="info" @click="findById(slotProps.data.id)" />
                        </div>
                    </template>
                </Column>
            </DataTable>
            <Drawer v-model:visible="visibleDrawerCadAccount" :dismissable="false" header="Conta" position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Nome</label>
                    <InputText id="account-name" v-model="account.accountName" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Agencia</label>
                    <InputText id="account-name" v-model="account.agency" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Numero da Conta</label>
                    <InputText id="account-name" v-model="account.accountNumber" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">Saldo</label>
                    <InputNumber id="account-name" v-model="account.balance" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>Tipo de Conta</label>
                    <SelectButton v-model="account.accountType" :options="typeOptions" optionLabel="label" optionValue="value" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>Banco</label>
                    <Select :options="listBank" fluid placeholder="Selecione um banco" filter optionLabel="name" v-model="bankSelected" showClear @before-show="findAllBank"></Select>
                </div>
                <div class="flex flex-col gap-1 mt-5">
                    <Button type="submit" severity="info" label="Save" @click="save" />
                    <Button type="submit" severity="danger" label="Cancelar" @click.prevent="visibleDrawerCadAccount = false" />
                </div>
            </Drawer>
        </div>
    </div>
</template>
