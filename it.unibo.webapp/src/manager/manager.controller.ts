import { Controller, Get, Render, Req, Res, UseGuards } from '@nestjs/common';
import { ApiTags } from '@nestjs/swagger';
import { Response } from 'express';
import { JwtAuthGuard } from 'src/auth/guard/jwt-auth.guard';
import { ManagerService } from './manager.service';

@ApiTags("manager")
@Controller('manager')
export class ManagerController {
  constructor(
    private readonly managerService: ManagerService
  ) {}

  @Get()
  async handleManagerLogin(@Req() req, @Res({ passthrough: true }) res: Response) {
    return req.cookies["X-AUTH-TOKEN"] != undefined ? 
      res.redirect("manager/console") : res.redirect("manager/login");
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
