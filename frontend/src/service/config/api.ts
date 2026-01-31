import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

const api: AxiosInstance = axios.create({
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
});

api.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token: string | null = localStorage.getItem('accessToken');
        if (token) {
            config.headers = config.headers || {};
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response: AxiosResponse) => response,
    async (error: AxiosError) => {
        if (error.response?.status === 401) {
            try {
                const refreshResponse = await axios.post('/api/v1/auth/refresh-token', {}, { withCredentials: true });

                const newAccessToken = refreshResponse.data.accessToken;
                console.log(newAccessToken);
                if (newAccessToken === null || newAccessToken === undefined) {
                    return Promise.reject(error);
                }

                localStorage.setItem('accessToken', newAccessToken);
                if (error.config) {
                    error.config.headers = error.config.headers || {};
                    error.config.headers['Authorization'] = `Bearer ${newAccessToken}`;
                    return api.request(error.config);
                }
            } catch (refreshError) {
                console.log(refreshError);
                localStorage.clear();
                // window.location.reload();
            }
        } else if (error.response?.status === 403) {
        }
        console.log(error);

        return Promise.reject(error);
    }
);

export default api;
