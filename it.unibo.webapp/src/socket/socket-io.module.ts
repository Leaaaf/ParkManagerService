import { Global, Module } from "@nestjs/common";
import { SocketIoProxyService } from "./socket-io.proxy.service";
import { SocketIoService } from "./socket-io.service";

@Global()
@Module({
  providers: [SocketIoService, SocketIoProxyService],
  exports: [SocketIoService, SocketIoProxyService],
})
export class SocketIoModule {}