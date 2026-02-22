import { PageResponse } from '@/common/model/page.response';
import api from '@/service/config/api';
import { format } from 'date-fns';
import { Incoming } from '../model/incoming';
import { IncomingResponse } from '../model/incoming.response';

class IncomingTransactionService {
    private endpoint: string = '/api/v1/incomings';

    public async findByFilter(): Promise<PageResponse<IncomingResponse>> {
        const response = await api.get<PageResponse<IncomingResponse>>(`${this.endpoint}/filter`);
        return response.data;
    }

    public async findById(id: string): Promise<Incoming> {
        const response = await api.get<Incoming>(`${this.endpoint}/${id}`);
        return response.data;
    }

    public async save(incoming: Incoming): Promise<any> {
        const payload: any = this.normalizePayloadIncoming(incoming);
        if (incoming.id) {
            return this.update(payload);
        } else {
            return this.create(payload);
        }
    }

    private async create(payload: Incoming): Promise<any> {
        const response = await api.post(`${this.endpoint}/create`, payload);
        return response;
    }

    private async update(payload: Incoming): Promise<any> {
        const response = await api.put(`${this.endpoint}/update`, payload);
        return response;
    }

    private normalizePayloadIncoming(incoming: Incoming) {
        return {
            ...incoming,
            paymentAt: incoming.paymentAt ? format(incoming.paymentAt, 'yyyy-MM-dd') : null
        };
    }
}

export default new IncomingTransactionService();
