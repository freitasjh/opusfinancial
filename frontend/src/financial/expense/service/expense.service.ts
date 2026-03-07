import { PageResponse } from "@/common/model/page.response";
import api from "@/service/config/api";
import { format } from 'date-fns';
import { ExpenseInfo } from "../dto/expense.info";
import { ExpenseResponseSave } from "../dto/expense.response.save";
import { ExpenseTransaction } from "../model/expense";

class ExpenseService {
    private endpoint = '/api/v1/exepenses'
  
    public async save(expense: ExpenseTransaction): Promise<ExpenseResponseSave> {
        const payload: any = this.normalisePayloadToSave(expense);
        if (expense.id) {
            return this.update(payload);
        } else {
            return this.create(payload);
        }
    }   

    private async create(expense: ExpenseTransaction): Promise<ExpenseResponseSave> {
        const response = await api.post<ExpenseResponseSave>(`${this.endpoint}/create`, expense);   
        return response.data;
    }

    private async update(expense: ExpenseTransaction): Promise<ExpenseResponseSave> {
        const response = await api.put<ExpenseResponseSave>(`${this.endpoint}/update`, expense);
        return response.data;
    }

    public async findById(id: string): Promise<ExpenseTransaction> {
        const response = await api.get<ExpenseTransaction>(`${this.endpoint}/${id}`);
        return response.data;
    }

    public async findByFilter(): Promise<PageResponse<ExpenseInfo>> {
        const response = await api.get<PageResponse<ExpenseInfo>>(`${this.endpoint}/filter`);
        return response.data;
    }

    private normalisePayloadToSave(expense: ExpenseTransaction): any {
        return {
            ...expense,
            paymentAt: expense.paymentAt ? format(expense.paymentAt, 'yyyy-MM-dd') : null,
            dueDate: expense.dueDate ? format(expense.dueDate, 'yyyy-MM-dd') : null,
            processed: expense.paymentAt ? true : false 
        };
    }
}

export default new ExpenseService();