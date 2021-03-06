import { IsEmail, IsNotEmpty } from "class-validator";

export class RequestSlotnumDto {
  @IsNotEmpty()
  @IsEmail()
  email: string;
}