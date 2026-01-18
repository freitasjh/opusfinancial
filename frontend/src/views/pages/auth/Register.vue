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

const handleRegister = async () => {
    const swal = inject<VueSweetalert2>('$swal');
    try {
        if (form.value.password !== confirmPassword.value) {
            dialog.handlerError('As senhas não conferem.');
            return;
        }

        if (!terms.value) {
            dialog.handlerError('Infome o aceite');
            return;
        }

        loading.value = true;
        const response = await UserAccountService.create(form.value);

        if (response) {
            dialog.handlerModalSuccessWithRedirect('Conta criada com sucesso. Você recebera um e-mail de confirmação', '/auth/login');
            router.push('/auth/login');
        }
    } catch (error: AxiosError | any) {
        dialog.handlerError(error);
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
                        <div class="text-surface-900 dark:text-surface-0 text-3xl font-medium mb-4">Crie sua Conta</div>
                        <span class="text-surface-500 dark:text-surface-400 font-medium line-height-3">Junte-se ao nosso sistema financeiro</span>
                    </div>

                    <div class="flex flex-col gap-6">
                        <div class="flex flex-col gap-2">
                            <label for="name" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">Nome da conta</label>
                            <InputText id="account-name" v-model="form.accountName" type="text" placeholder="Nome da conta" class="w-full md:w-[30rem]" />
                        </div>
                        <div class="flex flex-col gap-2">
                            <label for="name" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">Nome Completo</label>
                            <InputText id="name" v-model="form.name" type="text" placeholder="Seu nome" class="w-full md:w-[30rem]" />
                        </div>
                        <div class="flex flex-col gap-2">
                            <label for="name" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">CPF</label>
                            <InputText id="name" v-model="form.document" type="text" placeholder="Seu nome" class="w-full md:w-[30rem]" />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="email" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">Email</label>
                            <InputText id="email" v-model="form.email" type="text" placeholder="Informe um usuario valido" class="w-full md:w-[30rem]" />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="email" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">Usuario</label>
                            <InputText id="email" v-model="form.username" type="text" placeholder="email@exemplo.com" class="w-full md:w-[30rem]" />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="password" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">Senha</label>
                            <Password id="password" v-model="form.password" :toggleMask="true" placeholder="Senha segura" :feedback="true" promptLabel="Digite uma senha" weakLabel="Fraca" mediumLabel="Média" strongLabel="Forte" fluid />
                        </div>

                        <div class="flex flex-col gap-2">
                            <label for="confirmPassword" class="block text-surface-900 dark:text-surface-0 text-lg font-medium">Confirmar Senha</label>
                            <Password id="confirmPassword" v-model="confirmPassword" :toggleMask="true" :feedback="false" placeholder="Confirme a senha" fluid />
                        </div>

                        <div class="flex items-center gap-2 mt-2">
                            <Checkbox id="terms" v-model="terms" binary />
                            <label for="terms" class="text-surface-900 dark:text-surface-0">Eu aceito os <a class="font-bold text-primary cursor-pointer hover:underline">Termos e Condições</a></label>
                        </div>

                        <Button label="Registrar" class="w-full p-3 text-lg" :loading="loading" @click="handleRegister"></Button>

                        <div class="text-center mt-4">
                            <span class="text-surface-600 dark:text-surface-400 font-medium">Já possui conta? </span>
                            <a class="font-bold text-primary cursor-pointer no-underline ml-2 hover:underline" @click="router.push('/auth/login')">Fazer Login</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
