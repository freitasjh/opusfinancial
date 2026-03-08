<script setup lang="ts">
import { PageResponse } from '@/common/model/page.response';

import { useHandlerMessage } from '@/composoable/commons';
import { Bank } from '@/financial/bank/model/bank.model';
import bankService from '@/financial/bank/service/bank.service';
import { AxiosError } from 'axios';
import { Select, Tag } from 'primevue';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import AppFormDrawer from '@/components/AppFormDrawer.vue';
import { useDashboardStore } from '@/reporting/dashboard/store/dashboard.store';
import { Account } from '../../model/account.model';
import { AccountResponse } from '../../model/account.response';
import { AccountType } from '../../model/account.type';
import AccountService from '../../service/account.service';

const handlerMessage = useHandlerMessage();
const dashboardStore = useDashboardStore();

const loadingTable = ref<boolean>(false);
const loadingSave = ref<boolean>(false);
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

const home = ref({
    icon: 'pi pi-home',
    route: '/'
});

const items = ref([
    { label: t('register') },
    { label: t('account'), route: '/account' }
]);

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
        loadingSave.value = true;
        account.value.bankId = bankSelected.value?.id;

        await AccountService.save(account.value);
        await dashboardStore.fetchBalanceSummary();
        handlerMessage.toastSuccess(t('accountSavedSuccess'));
        visibleDrawerCadAccount.value = false;
        await findByFilter();
    } catch (error: AxiosError | any) {
        handlerMessage.error(error);
    } finally {
        loadingSave.value = false;
    }
};
</script>
<template>
    <div>
        <div class="">
            <Toolbar class="bg-surface-0 dark:bg-surface-900 shadow-sm p-5 rounded-2xl mb-4 border border-surface-200 dark:border-surface-800">
                <template #start>
                    <Button :label="t('new')" icon="pi pi-plus" severity="primary" class="mr-2" @click="openDrawerCad" />
                </template>
                <template #end>
                    <div class="flex items-center gap-2">
                        <Breadcrumb :home="home" :model="items">
                            <template #item="{ item, props }">
                                <router-link v-if="item.route" v-slot="{ href, navigate }" :to="item.route" custom>
                                    <a :href="href" v-bind="props.action" @click="navigate">
                                        <span :class="[item.icon, 'text-color']" />
                                        <span class="text-primary font-semibold">{{ item.label }}</span>
                                    </a>
                                </router-link>
                                <a v-else :href="item.url" :target="item.target" v-bind="props.action">
                                    <span class="text-surface-700 dark:text-surface-0">{{ item.label }}</span>
                                </a>
                            </template>
                        </Breadcrumb>
                    </div>
                </template>

            </Toolbar>
        </div>
        <div class="bg-surface-0 dark:bg-surface-900 shadow-sm p-6 rounded-2xl border border-surface-200 dark:border-surface-800">
            <DataTable ref="dt" v-model:selection="accountSelected" :value="pageAccountResult?.content" dataKey="id"
                stripedRows :paginator="true" :rows="30">
                <Column field="accountName" :header="t('description')" sortable style="min-width: 12rem" />
                <Column field="bank" :header="t('bank')" sortable style="min-width: 12rem" />
                <Column field="accountType" :header="t('accountType')" sortable style="min-width: 12rem">
                    <template #body="slotProps">
                        <Tag :value="t(slotProps.data.accountType)" severity="secondary" />
                    </template>
                </Column>
                <Column style="width: 10rem" :header="t('actions')">
                    <template #body="slotProps">
                        <div class="flex flex-wrap gap-2">
                            <Button type="button" icon="pi pi-pencil" rounded severity="info"
                                @click="findById(slotProps.data.id)" />
                        </div>
                    </template>
                </Column>
            </DataTable>
            <AppFormDrawer 
                :visible="visibleDrawerCadAccount" 
                @update:visible="visibleDrawerCadAccount = $event" 
                :header="t('account')" 
                :loading="loadingSave"
                @save="save"
                @cancel="visibleDrawerCadAccount = false"
            >
                <div class="flex flex-col gap-4 mt-2">
                    <div class="flex flex-col gap-1">
                        <label for="account-name" class="font-medium text-sm">{{ t('description') }}</label>
                        <InputText id="account-name" v-model="account.accountName" type="text" fluid />
                    </div>
                    <div class="flex flex-col gap-1">
                        <label for="agency" class="font-medium text-sm">{{ t('agency') }}</label>
                        <InputText id="agency" v-model="account.agency" type="text" fluid />
                    </div>
                    <div class="flex flex-col gap-1">
                        <label for="account-number" class="font-medium text-sm">{{ t('accountNumber') }}</label>
                        <InputText id="account-number" v-model="account.accountNumber" type="text" fluid />
                    </div>
                    <div class="flex flex-col gap-1">
                        <label for="balance" class="font-medium text-sm">{{ t('balance') }}</label>
                        <InputNumber id="balance" v-model="account.balance" mode="currency" currency="BRL" locale="pt-BR" fluid />
                    </div>
                    <div class="flex flex-col gap-1">
                        <label class="font-medium text-sm">{{ t('accountType') }}</label>
                        <SelectButton v-model="account.accountType" :options="typeOptions" optionLabel="label"
                            optionValue="value" class="w-full" />
                    </div>
                    <div class="flex flex-col gap-1">
                        <label class="font-medium text-sm">{{ t('bank') }}</label>
                        <Select :options="listBank" fluid :placeholder="t('selectBank')" filter optionLabel="name"
                            v-model="bankSelected" showClear @before-show="findAllBank"></Select>
                    </div>
                </div>
            </AppFormDrawer>
        </div>
    </div>
</template>