import api from "@/service/config/api";
import { AccountBalanceSummary } from "../model/account.balance.summary";

class DashboardService {
    private endpoint = '/api/v1/dashboards';

    public async findAccountBalanceSummary(): Promise<AccountBalanceSummary[]> {
        const response = await api.get(this.endpoint + '/account-summary-balance');
        return response.data;
    }
}

export default new DashboardService();