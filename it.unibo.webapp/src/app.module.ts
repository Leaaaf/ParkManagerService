import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ManagerModule } from './manager/manager.module';
import { ClientModule } from './client/client.module';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import { ConfigModule } from '@nestjs/config';
import { configuration } from './common/configuration';
import { validationSchema } from './common/validation';

@Module({
  imports: [ManagerModule, ClientModule, AuthModule, UserModule,
    ConfigModule.forRoot({
      envFilePath: ".env",
      isGlobal: true,
      load: [configuration],
      validationSchema: validationSchema
    })],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
