import { Injectable } from "@nestjs/common";
import { ClientProxy, ReadPacket, WritePacket } from "@nestjs/microservices";
import { SocketIoService } from "./socket-io.service";

@Injectable()
export class SocketIoProxyService extends ClientProxy {
  constructor(private readonly client: SocketIoService) {
    super();
  }

  async connect(): Promise<any> {
      this.client.getSocket();
      console.log("SocketIoProxyService | Connect client proxy");
  }

  async close() {
    this.client.getSocket().disconnect();
    console.log("SocketIoProxyService | Disconnect client proxy");
  }

  async dispatchEvent(packet: ReadPacket<any>): Promise<any> {
      this.client.getSocket().emit(packet.pattern, packet.data);
      return;
  }

  publish(packet: ReadPacket<any>, callback: (packet: WritePacket<any>) => void): VoidFunction {
    console.log("SocketIoProxyService | Message: " + packet);
    setTimeout(() => callback({ response: packet.data }), 5000);
    return () => console.log("teardown");
  }
}