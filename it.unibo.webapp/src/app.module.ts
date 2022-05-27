import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ManagerModule } from './manager/manager.module';
import { ClientModule } from './client/client.module';

@Module({
  imports: [ManagerModule, ClientModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
