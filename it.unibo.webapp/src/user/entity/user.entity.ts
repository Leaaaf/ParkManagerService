export class User {
  constructor (userId, username, password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }

  userId: number;
  username: string;
  password: string;
}