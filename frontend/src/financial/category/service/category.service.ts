import { PageResponse } from '@/common/model/page.response';
import api from '@/service/config/api';
import { Category } from '../model/category.model';
import { CategoryResponse } from '../model/category.response.mode';
import { CategoryFilter } from './../model/category.filter';

class CategoryService {
    private readonly _endpoint: string = '/api/v1/categories';

    public async findById(id: string): Promise<Category> {
        const response = await api.get(`${this._endpoint}/${id}`);
        return response.data;
    }

    public async findByFilter(filter: CategoryFilter | null): Promise<PageResponse<CategoryResponse>> {
        let query = '';
        if (filter != null) {
            query = `?keyword=${filter.keyword}&limit=${filter.limit}&page=${filter.page}`;
        }

        const response = await api.get(`${this._endpoint}/filter${query}`);

        return response.data;
    }

    public async findByParentId(parentId: string): Promise<any> {
        const response = await api.get(`${this._endpoint}/${parentId}/parent`);
        return response.data;
    }

    public async save(category: Category): Promise<any> {
        if (category.id == null) {
            return await this.create(category);
        }

        return await this.update(category);
    }

    private async create(category: Category): Promise<any> {
        const response = await api.post(`${this._endpoint}/create`, category);
        return response.data;
    }

    private async update(category: Category): Promise<any> {
        const response = await api.put(`${this._endpoint}/update`, category);
        return response.data;
    }
}

export default new CategoryService();
