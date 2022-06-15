import { CustomTransportStrategy, Server } from "@nestjs/microservices";
import { Socket } from "socket.io-client";

export class SocketIoStrategy extends Server implements CustomTransportStrategy {
  constructor(private client: Socket) {
    console.log("SocketIoStrategy")
    super();
  }

  listen(callback: () => void) {
    console.log("SocketIoStrategy | listen")
    this.client.on('connection', () => {
      console.log("connect");
    });
    this.client.on('error', (err) => {
      console.log(err);
    })

    this.messageHandlers.forEach((handler, pattern) => {
      this.client.on(pattern, (data: any) => {
        handler(data, this.client);
      });
    });

    callback();
  }

  close() {
    this.client.disconnect();
  }
}