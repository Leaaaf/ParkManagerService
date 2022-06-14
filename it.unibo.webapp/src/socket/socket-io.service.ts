import { Injectable } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import { Socket, io } from "socket.io-client";

@Injectable()
export class SocketIoService {
  constructor(private readonly configService: ConfigService) {}

  private socket: Socket;

  private connect() {
    this.socket = io(this.configService.get("WS_SERVER"));
    return this.socket;
  }

  getSocket() {
    if (!this.socket) {
      return this.connect()
    }
    return this.socket;
  }
}