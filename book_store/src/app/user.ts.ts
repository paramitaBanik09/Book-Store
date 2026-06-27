import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginReq, LoginResponseTO, User } from './user.ts/user.ts-module';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http:HttpClient){}

  baseurl:string = "http://localhost:8080/api/v1/user";

  public login(loginReq:LoginReq):Observable<LoginResponseTO>{
   return this.http.post<LoginResponseTO>(this.baseurl.concat("/login"),loginReq);
  }

  public register(userReq : User):Observable<User>{
    return this.http.post<User>(this.baseurl.concat("/register"),userReq);
  }

  public getUserList() : Observable<User[]>{
    return this.http.get<User[]>(this.baseurl.concat("/list"))
  }
}
