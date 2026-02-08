import { PageResponse } from '@/common/model/page.response';
import api from '@/service/config/api';
import { AccountInfoResponse } from '../model/account.info.response.model';
import { Account } from '../model/account.model';
import { AccountResponse } from '../model/account.response';

class AccountService {
    private readonly endpoint = '/api/v1/accounts';

    public async findByFilter(): Promise<PageResponse<AccountResponse>> {
        const response = await api.get<PageResponse<AccountResponse>>(`${this.endpoint}/filter`);
        return response.data;
    }

    public async findById(id: string): Promise<Account> {
        const response = await api.get<AccountInfoResponse>(`${this.endpoint}/${id}`);
        return response.data;
    }

    public async save(account: Account): Promise<AccountResponse> {
        if (account.id) {
            return this.update(account);
        }
        return this.create(account);
    }

    private async create(account: Account): Promise<AccountResponse> {
        const response = await api.post<AccountResponse>(this.endpoint, account);
        return response.data;
    }

    private async update(account: Account): Promise<AccountResponse> {
        const response = await api.put<AccountResponse>(`${this.endpoint}/${account.id}`, account);
        return response.data;
    }
}

export default new AccountService();
