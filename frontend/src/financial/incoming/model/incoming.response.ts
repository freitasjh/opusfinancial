export interface IncomingResponse {
    id: string;
    description: string;
    account: string;
    category: string;
    amount: number;
    paymentAt: Date;
    processedAt: Date;
}
