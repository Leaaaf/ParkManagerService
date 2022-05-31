import { ExecutionContext, Injectable } from "@nestjs/common";
import { AuthGuard } from "@nestjs/passport";
import { User } from "src/user/entity/user.entity";

@Injectable()
export class JwtAuthGuard extends AuthGuard("jwt") {

  canActivate(context: ExecutionContext) {
    return super.canActivate(context);
  }

  // handleRequest(err: any, user: any, info: any, context: any, status?: any): User {
  //     if (err || !user) {

  //     }
  // }
}