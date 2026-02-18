<script setup lang="ts">
import { PageResponse } from '@/common/model/page.response';

import { useHandlerMessage } from '@/composoable/commons';
import { Bank } from '@/financial/bank/model/bank.model';
import bankService from '@/financial/bank/service/bank.service';
import { AxiosError } from 'axios';
import { Select, Tag } from 'primevue';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n';
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
const { t } = useI18n();

onBeforeMount(async () => {
    await findByFilter();
});

const typeOptions = ref([
    { label: t('WALLET'), value: AccountType.WALLET },
    { label: t('INVESTMENT'), value: AccountType.INVESTMENT },
    { label: t('SAVING'), value: AccountType.SAVING },
    { label: t('CHECKING'), value: AccountType.CHECKING }
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
                    <Button :label="t('new')" icon="pi pi-plus" severity="info" class="mr-2" @click="openDrawerCad" />
                </template>
            </Toolbar>
            <DataTable ref="dt" v-model:selection="accountSelected" :value="pageAccountResult?.content" dataKey="id" :paginator="true" :rows="30">
                <Column field="accountName" :header="t('description')" sortable style="min-width: 12rem" />
                <Column field="bank" :header="t('bank')" sortable style="min-width: 12rem" />
                <Column field="accountType" :header="t('accountType')" sortable style="min-width: 12rem">
                    <template #body="slotProps">
                        <Tag :value="t(slotProps.data.accountType)" />
                    </template>
                </Column>
                <Column style="width: 10rem" :header="t('actions')">
                    <template #body="slotProps">
                        <div class="flex flex-wrap gap-2">
                            <Button type="button" icon="pi pi-pencil" rounded severity="info" @click="findById(slotProps.data.id)" />
                        </div>
                    </template>
                </Column>
            </DataTable>
            <Drawer v-model:visible="visibleDrawerCadAccount" :dismissable="false" header="Conta" position="right" class="!w-full md:!w-80 lg:!w-[30rem]">
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">{{ t('description') }}</label>
                    <InputText id="account-name" v-model="account.accountName" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">{{ t('agency') }}</label>
                    <InputText id="account-name" v-model="account.agency" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">{{ t('accountNumber') }}</label>
                    <InputText id="account-name" v-model="account.accountNumber" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label for="account-name">{{ t('balance') }}</label>
                    <InputNumber id="account-name" v-model="account.balance" type="text" fluid size="small" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>{{ t('accountType') }}</label>
                    <SelectButton v-model="account.accountType" :options="typeOptions" optionLabel="label" optionValue="value" />
                </div>
                <div class="flex flex-col gap-1 mt-2">
                    <label>{{ t('bank') }}</label>
                    <Select :options="listBank" fluid :placeholder="t('selectBank')" filter optionLabel="name" v-model="bankSelected" showClear @before-show="findAllBank"></Select>
                </div>
                <div class="flex flex-col gap-1 mt-5">
                    <Button type="submit" severity="info" :label="t('save')" @click="save" />
                    <Button type="submit" severity="danger" :label="t('cancel')" @click.prevent="visibleDrawerCadAccount = false" />
                </div>
            </Drawer>
        </div>
    </div>
</template>
