import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

import '@/assets/styles.scss';
import '@/assets/tailwind.css';
import Aura from '@primeuix/themes/aura';
import { createPinia } from 'pinia';
import PrimeVue from 'primevue/config';
import ConfirmationService from 'primevue/confirmationservice';
import ToastService from 'primevue/toastservice';
import 'sweetalert2/dist/sweetalert2.min.css';
import { createI18n } from 'vue-i18n';
import VueSweetalert2 from 'vue-sweetalert2';
import { message_en, message_pt_BR } from './config/i18n/messages';

const app = createApp(App);

const i18n = createI18n({
    locale: 'pt_BR',
    legacy: false,
    globalInjection: true,
    messages: {
        pt_BR: message_pt_BR,
        en: message_en
    }
});

app.use(createPinia());
app.use(router);
app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            darkModeSelector: '.app-dark'
        }
    }
});

app.use(ToastService);
app.use(ConfirmationService);
app.use(VueSweetalert2);
app.use(i18n);

app.mount('#app');
