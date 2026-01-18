import api from '@/service/config/api';
import type { AxiosResponse } from 'axios';

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}

export abstract class BaseService<T> {
    protected resource: string;
    protected apiVersion: string;

    // "v1" é o padrão, mas pode ser sobrescrito
    constructor(resource: string, apiVersion: string = 'v1') {
        this.resource = resource;
        this.apiVersion = apiVersion;
    }

    // Helper protegido para montar a URL: /v1/financial/accounts
    // Isso garante que se você mudar a lógica de URL no futuro, muda num lugar só.
    protected getUrl(suffix: string = ''): string {
        // Remove barras duplas se houver erro de digitação
        const cleanResource = this.resource.startsWith('/') ? this.resource : `/${this.resource}`;
        return `/api/${this.apiVersion}${cleanResource}${suffix}`;
    }

    public getAll(page = 0, size = 10): Promise<AxiosResponse<PageResponse<T>>> {
        const query = `?page=${page}&size=${size}`;
        return api.get<PageResponse<T>>(this.getUrl(query));
    }

    public getById(id: number): Promise<AxiosResponse<T>> {
        return api.get<T>(this.getUrl(`/${id}`));
    }

    public create(entity: T): Promise<AxiosResponse<T>> {
        return api.post<T>(this.getUrl(), entity);
    }

    public update(id: number, entity: T): Promise<AxiosResponse<T>> {
        return api.put<T>(this.getUrl(`/${id}`), entity);
    }

    public delete(id: number): Promise<void> {
        return api.delete(this.getUrl(`/${id}`));
    }
}
