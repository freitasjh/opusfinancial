import type { AxiosError } from 'axios';
import type { SweetAlert2 } from 'sweetalert2';
import { inject } from 'vue';
import { useRouter } from 'vue-router';

interface ValidationError {
    message: string;
}

// Interface para a resposta de erro da API
interface ApiErrorResponse {
    msg: string;
    errors?: ValidationError[];
}

interface CustomAxiosError extends AxiosError<ApiErrorResponse> {}

export function useHandlerMessage() {
    // Injetar SweetAlert2 com tipagem
    const swal = inject<SweetAlert2>('$swal');
    const router = useRouter();

    // Verificar se swal foi injetado corretamente
    if (!swal) {
        throw new Error('SweetAlert2 ($swal) não foi injetado corretamente.');
    }

    // Manipula erros de validação
    const errorValidation = (error: CustomAxiosError): void => {
        let errorMessage = '';
        if (error.response?.data?.errors) {
            errorMessage = error.response.data.errors.map((err: ValidationError) => `<p><b>${err.message}</b></p>`).join('');
        }

        // Exibir mensagem de erro com Swal
        swal.fire({
            icon: 'error',
            title: error.response?.data?.msg ?? 'Erro de Validação',
            html: errorMessage
        });
    };

    // Manipula erros genéricos
    const error = (error: CustomAxiosError | string): void => {
        let message = '';

        if (typeof error === 'string') {
            message = error;
        } else if (error.response && error.response.data && error.response.data.msg) {
            if (error.response.data.errors && Array.isArray(error.response.data.errors)) {
                errorValidation(error);
                return;
            } else {
                message = error.response.data.msg;
            }
        } else {
            message = error.message || 'Erro desconhecido';
        }

        // Exibir mensagem de erro com Swal
        swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: message
        });
    };

    // Manipula notificações de sucesso (toast)
    const toastSuccess = (message: string): void => {
        const Toast = swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast: HTMLElement) => {
                toast.onmouseenter = swal.stopTimer.bind(swal);
                toast.onmouseleave = swal.resumeTimer.bind(swal);
            }
        });

        Toast.fire({
            icon: 'success',
            title: message
        });
    };

    // Manipula modais de sucesso
    const modalSuccess = (message: string): void => {
        swal.fire({
            title: 'Success',
            text: message,
            icon: 'success'
        });
    };

    const modalSuccessWithRedirect = (message: string, redirectUrl: string): void => {
        swal.fire({
            title: 'Success',
            text: message,
            icon: 'success'
        }).then((result: any) => {
            if (result.isConfirmed) {
                router.push(redirectUrl);
            }
        });
    };

    return {
        error,
        toastSuccess,
        modalSuccess,
        modalSuccessWithRedirect
    };
}
