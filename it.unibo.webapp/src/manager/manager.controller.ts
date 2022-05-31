import { Controller, Get, Redirect, Render, UseGuards } from '@nestjs/common';
import { JwtAuthGuard } from 'src/auth/guard/jwt-auth.guard';
import { ManagerService } from './manager.service';

@Controller('manager')
export class ManagerController {
  constructor(private readonly managerService: ManagerService) {}

  @Get()
  @UseGuards(JwtAuthGuard)
  @Render('manager/main-console')
  root() {
  }

  @Get("login")
  @Render("manager/login")
  login() {

  }
}
