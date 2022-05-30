import { Injectable } from '@nestjs/common';
import { type } from 'os';
import { User } from './entity/user.entity';

@Injectable()
export class UserService {
  private readonly users = [
    new User(1,"manager","issUnibo21")
  ]

  async findOne(username: string): Promise<User | undefined> {
    return this.users.find((user) => user.username === username)
  }
}
