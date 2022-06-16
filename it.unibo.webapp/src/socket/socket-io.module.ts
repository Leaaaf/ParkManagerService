import { Global, Module } from "@nestjs/common";
import { SocketIoController } from "./socket-io.controller";
import { SocketIoProxyService } from "./socket-io.proxy.service";
import { SocketIoService } from "./socket-io.service";

@Global()
@Module({
  controllers: [SocketIoController],
  providers: [SocketIoService, SocketIoProxyService],
  exports: [SocketIoService, SocketIoProxyService],
})
export class SocketIoModule {}