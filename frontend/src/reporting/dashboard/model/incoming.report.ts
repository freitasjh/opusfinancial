export interface IncomingReport {
    id: string;
    description: string;
    amount: number;
    paymentAt: Date | null;
    createAt: Date;
    account: string;
    category: string;
}