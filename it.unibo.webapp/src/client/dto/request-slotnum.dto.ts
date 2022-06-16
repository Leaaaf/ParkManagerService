import { IsEmail, IsNotEmpty } from "class-validator";

export class CreateRequestSlotnumDto {
  @IsNotEmpty()
  @IsEmail()
  email: string;
}