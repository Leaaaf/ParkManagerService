import { Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { User } from 'src/user/entity/user.entity';
import { UserService } from 'src/user/user.service';

@Injectable()
export class AuthService {
  constructor(private userService: UserService,
              private jwtService: JwtService) {}

  async validateUser(username: string, password: string): Promise<any> {
    const user = await this.userService.findOne(username);
    if (user && user.password === password) {
      const {password, ...result } = user;
      return result;
    }
    return null;
  }

  async login(user: User) {
    const payload = {username: user.username, sub: user.userId};
    try {
      return {
        access_token: this.jwtService.sign(payload)
      }
    } catch (e) {
      return {
        access_token: null
      }
    }
  }
}
