import { Injectable } from '@nestjs/common';

@Injectable()
export class ManagerService {
  private fan = "";

  setFan(val) {
    this.fan = val; 
    console.log("ManagerService::setFan::", this.fan)
  }

  getFan() {
    console.log("ManagerService::getFan::", this.fan);
    return this.fan;
  }
}
