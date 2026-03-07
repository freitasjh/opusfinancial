export interface ExpenseInfo {
    id: string;
    description: string;
    paymentAt: Date;
    createAt: Date;
    value: number;
    category: string;
    account: string;
    type: string;
    processed: boolean
}