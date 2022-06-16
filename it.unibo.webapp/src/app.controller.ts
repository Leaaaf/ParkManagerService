import { Controller, Get, Post, Redirect, Render, Req, Res, UseGuards } from '@nestjs/common';
import { ApiTags } from '@nestjs/swagger';
import { Response } from 'express';
import { AppService } from './app.service';
import { AuthService } from './auth/auth.service';
import { LocalAuthGuard } from './auth/guard/local-auth.guard';

@ApiTags("core")
@Controller()
export class AppController {
  constructor(
    private readonly appService: AppService,
    private authService: AuthService
  ) {}

  @Get()
  @Render('index')
  root() {
    return {message: this.appService.getHello()}
  }

  @Post("auth/login")
  @UseGuards(LocalAuthGuard)
  @Redirect("/manager")
  async login(@Req() req, @Res({ passthrough: true }) res: Response) {
    const accessToken = (await this.authService.login(req.user)).access_token;
    res.cookie("X-AUTH-TOKEN", accessToken)
  }

  @Get("auth/logout")
  @Redirect("/")
  async logout(@Res({ passthrough: true }) res: Response) {
    res.cookie("X-AUTH-TOKEN", "", {expires: new Date()})
  }
}
