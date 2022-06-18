import { Body, Controller, Get, Post, Redirect, Render, Req, Res, UsePipes, ValidationPipe } from '@nestjs/common';
import { ApiTags } from '@nestjs/swagger';
import { Response } from 'express';
import { ClientService } from './client.service';
import { EnterCarDto } from './dto/enter-car.dto';
import { PickupCarDto } from './dto/pickup-car.dto';
import { RequestSlotnumDto } from './dto/request-slotnum.dto';

@ApiTags("client")
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
  sendRequestSlotnum(@Body() body: RequestSlotnumDto) {    
    this.clientService.notifyIntrest(body.email);
    return {message: "Request sent, check your email"}
  }

  @Post("entercar/send")
  @Render('client/enter-car')
  @UsePipes(new ValidationPipe({transform: true}))
  sendEnterCar(@Body() body: EnterCarDto) {   
    if (body.slotnum < 1 || body.slotnum > 6)
      return {message: "Slotnum must be in the range 1, 6"}
    this.clientService.enterCar(body.email, body.slotnum);
    return {message: "Request sent, check your email"}
  }

  @Post("pickupcar/send")
  @Render('client/pickup-car')
  @UsePipes(new ValidationPipe({transform: true}))
  sendPickupCar(@Body() body: PickupCarDto) {    
    this.clientService.pickupCar(body.email, body.token);
    return {message: "Request sent, check your email"}
  }
}
