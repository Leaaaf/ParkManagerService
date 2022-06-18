import { IsEmail, IsNotEmpty, IsNumber, IsNumberString, Max, Min } from "class-validator";

export class EnterCarDto {
  @IsNotEmpty()
  @IsEmail()
  email: string;

  @IsNotEmpty()
  @IsNumberString()
  slotnum: number;
}