import { defineStore } from 'pinia';
import { computed, ref } from 'vue';
import { UserProfileVO } from '../model/user.profile.vo';
import UserService from '../service/UserService';

export const useAuthStore = defineStore('auth', () => {
    //State
    const token = ref<string | null>(localStorage.getItem('accessToken'));
    const user = ref<UserProfileVO | null>(JSON.parse(localStorage.getItem('user') || 'null'));

    const isAuthenticated = computed(() => !!token.value);
    const userName = computed(() => user.value?.name || 'Visitante');

    async function setSession(accessToken: string, userId: string) {
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('userId', userId);

        const userProfileVO = await UserService.findProfileById(userId);
        localStorage.setItem('user', JSON.stringify(userProfileVO));

        token.value = accessToken;
        user.value = userProfileVO;
    }

    function logout() {
        token.value = null;
        user.value = null;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('userId');
        localStorage.removeItem('user');
    }

    return { token, user, isAuthenticated, userName, setSession, logout };
});
