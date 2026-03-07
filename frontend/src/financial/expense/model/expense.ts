export interface ExpenseTransaction {
    id: string;
    description: string;
    paymentAt: Date;
    dueDate: Date | null;
    amount: number;
    categoryId: string | null;
    accountId: string | null;
    type: string;
    status: string;
}