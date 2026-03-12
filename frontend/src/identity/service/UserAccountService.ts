import api from '@/service/config/api';
import { AxiosResponse } from 'axios';
import { UserAccountModel } from '../model/user.account.model';

class UserAccountService {
    _endpoint: String = '/api/v1/user-accounts';

    public async create(userAccount: UserAccountModel): Promise<AxiosResponse> {
        const response = await api.post(`${this._endpoint}/create`, userAccount);
        return response;
    }
}

export default new UserAccountService();
