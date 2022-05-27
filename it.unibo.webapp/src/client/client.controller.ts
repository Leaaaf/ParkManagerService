import { Controller, Get, Redirect, Render, Res } from '@nestjs/common';
import { ClientService } from './client.service';

@Controller('client')
export class ClientController {
  constructor(private readonly clientService: ClientService) {}

  @Get()
  root(@Res() res) {
    return res.redirect("/client/requestslotnum")
  }

  @Get("requestslotnum")
  @Render('client/home')
  requestSlotnum() {
  }

  @Get("entercar")
  @Render('client/enter-car')
  enterCar() {
  }

  @Get("pickupcar")
  @Render('client/pickup-car')
  pickupCar() {
  }
}
