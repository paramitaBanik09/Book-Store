import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenTs {
  set token(t:string){
    localStorage.setItem("token",t)
  }
  get token():string | null{
    return localStorage.getItem("token");
  }
}
