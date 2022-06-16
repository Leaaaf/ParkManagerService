import { ConfigService } from '@nestjs/config';
import { NestFactory } from '@nestjs/core';
import { NestExpressApplication } from '@nestjs/platform-express';
import { join } from 'path';
import { AppModule } from './app.module';
import * as cookieParser from 'cookie-parser';
import { SocketIoService } from './socket/socket-io.service';
import { MicroserviceOptions } from '@nestjs/microservices';
import { SocketIoStrategy } from './socket/strategy/socket-io.strategy';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { getFromContainer, MetadataStorage } from 'class-validator';
import { validationMetadatasToSchemas } from 'class-validator-jsonschema';
import { writeFileSync } from 'fs';

async function bootstrap() {
  const app = await NestFactory.create<NestExpressApplication>(AppModule);
  
  const configService = app.get<ConfigService>(ConfigService);
  const socketIoService = app.get<SocketIoService>(SocketIoService);

  app.enableCors();
  app.use(cookieParser());
  app.useStaticAssets(join(__dirname, '..', 'public'));
  app.setBaseViewsDir(join(__dirname, '..', 'views'));
  app.setViewEngine('hbs');

  app.connectMicroservice<MicroserviceOptions>({
    strategy: new SocketIoStrategy(socketIoService.getSocket()),
  });

  const OPENAPI_FILE = "parkingmanagerservice-webapp-openapi.json";
  const config = new DocumentBuilder()
    .setTitle("Parking Manager Service")
    .setDescription("Final task of ISS @Unibo")
    .setVersion("1.0")
    .addTag("iss")
    .build();
  const document = SwaggerModule.createDocument(app, config);

  // Creating all the swagger schemas based on the class validator decorator
  const metadatas = (getFromContainer(MetadataStorage) as any).validationMetadatas;
  const schemas = validationMetadatasToSchemas(metadatas);

  if (document && document.components) document.components.schemas = schemas;

  SwaggerModule.setup("api", app, document);

  writeFileSync(OPENAPI_FILE, JSON.stringify(document), { encoding: "utf8" });

  await app.startAllMicroservices();
  await app.listen(configService.get<number>("PORT"));
}
bootstrap();
