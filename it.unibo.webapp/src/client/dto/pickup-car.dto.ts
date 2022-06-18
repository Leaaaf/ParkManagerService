import { IsEmail, IsNotEmpty } from "class-validator";

export class PickupCarDto {
  @IsNotEmpty()
  @IsEmail()
  email: string;
  
  @IsNotEmpty()
  token: string;
}