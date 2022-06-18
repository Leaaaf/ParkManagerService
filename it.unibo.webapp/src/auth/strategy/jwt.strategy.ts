import { Injectable } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { Request } from "express-serve-static-core";
import { Strategy } from "passport-jwt";

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor() {
    super({
      jwtFromRequest: JwtStrategy.extractJwtFromCookies,
      ignoreExpiration: false,
      secretOrKey: process.env.JWT_SECRET,
    });
  }

  async validate(payload: any) {
    return { userId: payload.sub, username: payload.username };
  }

  /**
   * Check token expiration. If valid return token else null
   * @param req Request
   * @returns token or null
   */
  private static extractJwtFromCookies(req: Request) {
    return req.cookies["X-AUTH-TOKEN"]
  }
}
