import { CategoryType } from './category.type';

export interface Category {
    id: string;
    parentId: string;
    name: string;
    colorHex: string;
    iconCode: string;
    categoryType: CategoryType;
}
