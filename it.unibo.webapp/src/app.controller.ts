import { Controller, Get, Post, Render, Request, Res, UseGuards } from '@nestjs/common';
import { AppService } from './app.service';
import { AuthService } from './auth/auth.service';
import { LocalAuthGuard } from './auth/guard/local-auth.guard';

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
  async login(@Request() req, @Res({ passthrough: true }) res) {
    return this.authService.login(req.user);
  }
}
