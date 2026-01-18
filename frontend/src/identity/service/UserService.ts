import api from '@/service/config/api';
import { UserProfileVO } from '../model/user.profile.vo';

class UserService {
    _endpoint: string = '/api/v1/users';

    public async findProfileById(id: string): Promise<UserProfileVO> {
        const response = await api.get<UserProfileVO>(`${this._endpoint}/${id}/profile`);
        return response.data;
    }
}

export default new UserService();
