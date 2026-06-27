export interface LoginReq{
  username : string;
  password : string;
}

export interface User{
  firstName : string;
  lastName : string;
  email : string;
  password:string;
}

export interface LoginResponseTO{
  "token":string;
  "username":string;
}
