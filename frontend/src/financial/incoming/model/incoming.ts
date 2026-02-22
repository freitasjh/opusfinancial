export interface Incoming {
    id: string;
    description: string;
    accountId: string;
    categoryId: string;
    amount: number;
    paymentAt: Date;
    processedAt: Date;
}
