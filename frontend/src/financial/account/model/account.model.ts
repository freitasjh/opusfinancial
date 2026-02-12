import { AccountType } from './account.type';
export interface Account {
    id: string;
    accountName: string;
    balance: number;
    bankId: string | null;
    accountType: AccountType;
    accountNumber: string;
    agency: string;
}
