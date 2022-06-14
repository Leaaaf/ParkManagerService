import { ConfigService } from '@nestjs/config';
import { NestFactory } from '@nestjs/core';
import { NestExpressApplication } from '@nestjs/platform-express';
import { join } from 'path';
import { AppModule } from './app.module';
import * as cookieParser from 'cookie-parser';
import { SocketIoService } from './socket/socket-io.service';
import { MicroserviceOptions } from '@nestjs/microservices';
import { SocketIoStrategy } from './socket/strategy/socket-io.strategy';

async function bootstrap() {
  const app = await NestFactory.create<NestExpressApplication>(AppModule);
  
  const configService = app.get<ConfigService>(ConfigService);
  const socketIoService = app.get<SocketIoService>(SocketIoService);

  app.use(cookieParser());
  app.useStaticAssets(join(__dirname, '..', 'public'));
  app.setBaseViewsDir(join(__dirname, '..', 'views'));
  app.setViewEngine('hbs');

  app.connectMicroservice<MicroserviceOptions>({
    strategy: new SocketIoStrategy(socketIoService.getSocket()),
  });

  await app.startAllMicroservices();
  await app.listen(configService.get<number>("PORT"));
}
bootstrap();
