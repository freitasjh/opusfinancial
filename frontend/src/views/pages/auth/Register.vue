<script setup lang="ts">
import { inject, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useHandlerMessage } from '@/composoable/commons';
import type { UserAccountModel } from '@/identity/model/user.account.model';
import UserAccountService from '@/identity/service/UserAccountService';
import { AxiosError } from 'axios';
import VueSweetalert2 from 'vue-sweetalert2';
import { useI18n } from 'vue-i18n';

const router = useRouter();
const dialog = useHandlerMessage();
const { t } = useI18n();

const form = ref<UserAccountModel>({
    accountName: '',
    name: '',
    document: '',
    email: '',
    username: '',
    password: ''
});

const confirmPassword = ref('');
const terms = ref(false);
const loading = ref(false);

const handleRegister = async () => {
    try {
        if (form.value.password !== confirmPassword.value) {
            dialog.error(t('passwordsDoNotMatch'));
            return;
        }

        if (!terms.value) {
            dialog.error(t('informAcceptance'));
            return;
        }

        loading.value = true;
        const response = await UserAccountService.create(form.value);

        if (response) {
            dialog.modalSuccessWithRedirect(t('accountCreatedSuccess'), '/auth/login');
        }
    } catch (error: AxiosError | any) {
        dialog.error(error);
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] p-4">
        <div class="w-full max-w-[600px]">
            <div class="bg-surface-0 dark:bg-surface-900 shadow-xl rounded-3xl p-8 md:p-12 border border-surface-200 dark:border-surface-800">
                <div class="text-center mb-10">
                    <h1 class="text-surface-900 dark:text-surface-0 text-3xl font-bold mb-3">{{ t('createAccount') }}</h1>
                    <p class="text-surface-500 dark:text-surface-400 font-medium">{{ t('joinUs') }}</p>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="flex flex-col gap-2">
                        <label for="account-name" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('accountName') }}</label>
                        <InputText id="account-name" v-model="form.accountName" type="text" :placeholder="t('accountName')" class="w-full" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="name" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('fullName') }}</label>
                        <InputText id="name" v-model="form.name" type="text" :placeholder="t('yourName')" class="w-full" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="document" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('document') }}</label>
                        <InputText id="document" v-model="form.document" type="text" :placeholder="t('document')" class="w-full" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="email" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('email') }}</label>
                        <InputText id="email" v-model="form.email" type="text" :placeholder="t('email')" class="w-full" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="username" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('username') }}</label>
                        <InputText id="username" v-model="form.username" type="text" :placeholder="t('validUser')" class="w-full" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="password" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('password') }}</label>
                        <Password id="password" v-model="form.password" :toggleMask="true" :placeholder="t('securePassword')" :feedback="true" :promptLabel="t('typePassword')" :weakLabel="t('weak')" :mediumLabel="t('medium')" :strongLabel="t('strong')" fluid />
                    </div>
                    <div class="flex flex-col gap-2 md:col-span-2">
                        <label for="confirmPassword" class="text-surface-900 dark:text-surface-0 font-medium text-sm">{{ t('confirmPassword') }}</label>
                        <Password id="confirmPassword" v-model="confirmPassword" :toggleMask="true" :feedback="false" :placeholder="t('confirmYourPassword')" fluid />
                    </div>
                </div>

                <div class="flex flex-col gap-6 mt-8">
                    <div class="flex items-center gap-2">
                        <Checkbox id="terms" v-model="terms" binary />
                        <label for="terms" class="text-surface-900 dark:text-surface-0 text-sm">
                            {{ t('acceptTerms') }} <a class="font-bold text-primary cursor-pointer hover:underline">{{ t('termsAndConditions') }}</a>
                        </label>
                    </div>

                    <Button :label="t('registerAction')" class="w-full p-4 text-lg font-bold" :loading="loading" @click="handleRegister"></Button>

                    <div class="text-center">
                        <span class="text-surface-600 dark:text-surface-400 font-medium text-sm">{{ t('alreadyHaveAccount') }} </span>
                        <a class="font-bold text-primary cursor-pointer no-underline ml-2 hover:underline text-sm" @click="router.push('/auth/login')">
                            {{ t('makeLogin') }}
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
