import { Injectable } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import { JwtService } from "@nestjs/jwt";
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
    const jwt = new JwtService();
    const decoded_token = jwt.decode(req.cookies["X-AUTH-TOKEN"]);

    let now: string | number = Date.now().toString()
    now = Number(now.substring(0, now.length - 3))

    return decoded_token["exp"] - now > 0 ? req.cookies["X-AUTH-TOKEN"] : null
  }
}
