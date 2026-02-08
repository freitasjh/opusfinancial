import { PageResponse } from '@/common/model/page.response';
import api from '@/service/config/api';
import { Bank } from '../model/bank.model';

class BankService {
    _endpoint: string = '/api/v1/banks';

    public async findByFilter(page: number = 0, keyword: string = ''): Promise<PageResponse<Bank>> {
        const response = await api.get(`${this._endpoint}/filter?page=${page}&keyword=${keyword}`);
        return response.data;
    }
}

export default new BankService();
