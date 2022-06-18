import { Controller, Get, Render, Req, Res, UseGuards } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { ApiTags } from '@nestjs/swagger';
import { Response } from 'express';
import { JwtAuthGuard } from 'src/auth/guard/jwt-auth.guard';

@ApiTags("manager")
@Controller('manager')
export class ManagerController {
  constructor(
    private readonly jwtService: JwtService
  ) {}

  @Get()
  async handleManagerLogin(@Req() req, @Res({ passthrough: true }) res: Response) {
    if (req.cookies["X-AUTH-TOKEN"] != undefined) {
      const decoded_token = this.jwtService.decode(req.cookies["X-AUTH-TOKEN"]);
      let now: string | number = Date.now().toString()
      now = Number(now.substring(0, now.length - 3))

      if (decoded_token["exp"] - now > 0)
        return res.redirect("manager/console")
    }
    return res.redirect("manager/login");
  }

  @Get("console")
  @UseGuards(JwtAuthGuard)
  @Render('manager/main-console')
  console(@Res() res: Response) {
  }

  @Get("login")
  @Render("manager/login")
  login() {
  }
}
