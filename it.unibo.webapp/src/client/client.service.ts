import { Injectable } from '@nestjs/common';
import { SocketIoProxyService } from 'src/socket/socket-io.proxy.service';

@Injectable()
export class ClientService {

  constructor(private readonly sokcetIoProxyService: SocketIoProxyService) {}

  async notifyIntrest(email: string) {
    const payload = "msg(enter, request, python, parkingmanagerservice, enter(\""+email+"\"),1)\n"
    this.sokcetIoProxyService.send("parkingmanagerservice", payload).subscribe(res => {
      console.log("ClientService | notifyIntrest | ", res)
    });
  }

  async enterCar(email: string, slotnum: number) {
    const payload = "msg(entercar, request, python, parkingmanagerservice, entercar(\""+slotnum+"\",\""+email+"\"),1)\n"
    this.sokcetIoProxyService.send("parkingmanagerservice", payload).subscribe(res => {
      console.log("ClientService | enterCar | ", res);
    })
  }

  async pickupCar(email: string, token: string) {
    const payload = "msg(pickup, request, python, parkingmanagerservice, pickup(\""+token+"\",\""+email+"\"),1)\n"
    this.sokcetIoProxyService.send("parkingmanagerservice", payload).subscribe(res => {
      console.log("ClientService | pickupCar | ", res);
    })
  }
}
