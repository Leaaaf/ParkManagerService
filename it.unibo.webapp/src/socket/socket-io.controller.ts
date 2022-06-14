import { Controller } from "@nestjs/common";
import { Ctx, MessagePattern, Payload } from "@nestjs/microservices";
import { Socket } from "socket.io-client";

@Controller()
export class SocketIoController {
  @MessagePattern("fan")
  handleSendHello(@Payload() data: string, @Ctx() client: Socket) {
    console.log("got fan from parking manager service", data);
    const responseMessage = 'Fan Payload';
    client.emit('fanResponse', responseMessage);
  }
}