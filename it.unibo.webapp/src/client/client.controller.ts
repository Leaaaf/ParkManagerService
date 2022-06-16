import { Body, Controller, Get, Post, Redirect, Render, Req, Res, UsePipes, ValidationPipe } from '@nestjs/common';
import { Response } from 'express';
import { ClientService } from './client.service';
import { CreateRequestSlotnumDto } from './dto/request-slotnum.dto';

@Controller('client')
export class ClientController {
  constructor(private readonly clientService: ClientService) {}

  @Get()
  root(@Res() res) {
    return res.redirect("/client/requestslotnum")
  }

  @Get("requestslotnum")
  @Render('client/home')
  requestSlotnum(@Req() req) {
  }

  @Get("entercar")
  @Render('client/enter-car')
  enterCar() {
  }

  @Get("pickupcar")
  @Render('client/pickup-car')
  pickupCar() {
  }

  @Post("requestslotnum/send")
  @Render('client/home')
  @UsePipes(new ValidationPipe({transform: true}))
  sendRequestSlotnum(@Body() body: CreateRequestSlotnumDto) {    
    this.clientService.notifyIntrest(body.email);
    return {message: "Request sent, check your email"}
  }
}
