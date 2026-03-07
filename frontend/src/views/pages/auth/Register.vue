<script setup lang="ts">
import { inject, ref } from 'vue';
import { useRouter } from 'vue-router';

// Componentes PrimeVue
import { useHandlerMessage } from '@/composoable/commons';
import type { UserAccountModel } from '@/identity/model/user.account.model';
import UserAccountService from '@/identity/service/UserAccountService';
import { AxiosError } from 'axios';
import VueSweetalert2 from 'vue-sweetalert2';

const router = useRouter();
const dialog = useHandlerMessage();

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

const { t } = useI18n();

const handleRegister = async () => {
    const swal = inject<VueSweetalert2>('$swal');
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
            router.push('/auth/login');
        }
    } catch (error: AxiosError | any) {
        dialog.error(error);
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] overflow-hidden">
        <div class="flex flex-col items-center justify-center">
            <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--p-primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full bg-surface-0 dark:bg-surface-900 py-20 px-8 sm:px-20 rounded-[53px] shadow-lg">
                    <div class="text-center mb-8">
                        <div class="text-surface-900 dark:text-surface-0 text-3xl font-medium mb-4">{{ t('createAccount') }}</div>
                        <span class="text-surface-500 dark:text-surface-400 font-medium line-height-3">{{ t('joinUs') }}</span>
                    </div>

                    <div class="flex flex-col gap-6">
                        <div class="flex flex-col gap-2">
                            <label for="name" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('accountName') }}</label>
                            <InputText id="account-name" v-model="form.accountName" type="text" :placeholder="t('accountName')" class="w-full md:w-[30rem]" />
                        </div>
                        <div class="flex flex-col gap-2">
                            <label for="name" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('fullName') }}</label>
                            <InputText id="name" v-model="form.name" type="text" :placeholder="t('yourName')" class="w-full md:w-[30rem]" />
                        </div>
                        <div class="flex flex-col gap-2">
                            <label for="name" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('document') }}</label>
                            <InputText id="name" v-model="form.document" type="text" :placeholder="t('document')" class="w-full md:w-[30rem]" />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="email" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('email') }}</label>
                            <InputText id="email" v-model="form.email" type="text" :placeholder="t('email')" class="w-full md:w-[30rem]" />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="email" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('username') }}</label>
                            <InputText id="email" v-model="form.username" type="text" :placeholder="t('validUser')" class="w-full md:w-[30rem]" />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="password" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('password') }}</label>
                            <Password id="password" v-model="form.password" :toggleMask="true" :placeholder="t('securePassword')" :feedback="true" :promptLabel="t('typePassword')" :weakLabel="t('weak')" :mediumLabel="t('medium')" :strongLabel="t('strong')" fluid />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="confirmPassword" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">{{ t('confirmPassword') }}</label>
                            <Password id="confirmPassword" v-model="confirmPassword" :toggleMask="true" :feedback="false" :placeholder="t('confirmYourPassword')" fluid />
                        </div>

                        <div class="flex items-center gap-2 mt-2">
                            <Checkbox id="terms" v-model="terms" binary />
                            <label for="terms" class="text-surface-900 dark:text-surface-0">{{ t('acceptTerms') }} <a class="font-bold text-primary cursor-pointer hover:underline">{{ t('termsAndConditions') }}</a></label>
                        </div>

                        <Button :label="t('registerAction')" class="w-full p-3 text-lg" :loading="loading" @click="handleRegister"></Button>

                        <div class="text-center mt-4">
                            <span class="text-surface-600 dark:text-surface-400 font-medium">{{ t('alreadyHaveAccount') }} </span>
                            <a class="font-bold text-primary cursor-pointer no-underline ml-2 hover:underline" @click="router.push('/auth/login')">{{ t('makeLogin') }}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
