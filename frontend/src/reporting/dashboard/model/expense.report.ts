export interface ExpenseReport {
    id: string;
    description: string;
    amount: number;
    paymentAt: Date | null;
    createAt: Date;
    dueDate: Date
    account: string;
    category: string;
}