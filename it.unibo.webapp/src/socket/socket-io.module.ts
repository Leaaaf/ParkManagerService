import { Global, Module } from "@nestjs/common";
import { SocketIoController } from "./socket-io.controller";
import { SocketIoProxyService } from "./socket-io.proxy.service";
import { SocketIoService } from "./socket-io.service";

@Global()
@Module({
  providers: [SocketIoService, SocketIoProxyService],
  controllers: [SocketIoController],
  exports: [SocketIoService, SocketIoProxyService],
})
export class SocketIoModule {}