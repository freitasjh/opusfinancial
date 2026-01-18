import api from '@/service/config/api';
import { AxiosResponse } from 'axios';

class AuthenticateService {
    public async sigIn(username: string, password: string): Promise<AxiosResponse> {
        const endpoint = '/api/v1/auth/login';
        const response = await api.post(endpoint, { username, password });
        return response;
    }
}

export default new AuthenticateService();
