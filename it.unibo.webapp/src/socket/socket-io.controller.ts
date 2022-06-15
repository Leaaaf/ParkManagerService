import { Controller } from "@nestjs/common";
import { Ctx, MessagePattern, Payload } from "@nestjs/microservices";
import { Socket } from "socket.io-client";

@Controller()
export class SocketIoController {
  @MessagePattern("carparking")
  handleWsCarParking(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got carparking from parking manager service", data);
    const responseMessage = 'Car Parking Payload';
    client.emit('carparkingResponse', responseMessage);
  }

  @MessagePattern("trolley")
  handleWsTrolley(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got trolley from parking manager service", data);
    const responseMessage = 'Trolley Payload';
    client.emit('trolleyResponse', responseMessage);
  }

  @MessagePattern("fan")
  handleWsFan(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got fan from parking manager service", data);
    const responseMessage = 'Fan Payload';
    client.emit('fanResponse', responseMessage);
  }

  @MessagePattern("weightsensor")
  handleWsWeightSensor(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got weight sensor from parking manager service", data);
    const responseMessage = 'Weight Sensor Payload';
    client.emit('weightsensorResponse', responseMessage);
  }

  @MessagePattern("thermometer")
  handleWsThermometer(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got thermometer from parking manager service", data);
    const responseMessage = 'Thermometer Payload';
    client.emit('thermometerResponse', responseMessage);
  }

  @MessagePattern("outsonar")
  handleWsOutsonar(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got outsonar from parking manager service", data);
    const responseMessage = 'Outsonar Payload';
    client.emit('outsonarResponse', responseMessage);
  }
}