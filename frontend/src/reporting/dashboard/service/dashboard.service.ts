import api from "@/service/config/api";
import { AccountBalanceSummary } from "../model/account.balance.summary";
import { ExpenseReport } from "../model/expense.report";
import { IncomingReport } from "../model/incoming.report";

class DashboardService {
    private endpoint = '/api/v1/dashboards';

    public async findAccountBalanceSummary(): Promise<AccountBalanceSummary[]> {
        const response = await api.get(this.endpoint + '/account-summary-balance');
        return response.data;
    }

    public async findLastExpense(): Promise<ExpenseReport[]> {
        const response = await api.get<ExpenseReport[]>(this.endpoint + '/last-expenses');
        return response.data;
    }

    public async findLastIncoming(): Promise<IncomingReport[]> {
        const response = await api.get<IncomingReport[]>(this.endpoint + '/last-incoming');
        return response.data;
    }
}

export default new DashboardService();