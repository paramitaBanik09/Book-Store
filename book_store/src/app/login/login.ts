import { Component } from '@angular/core';
import { LoginReq, User } from '../user.ts/user.ts-module';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.ts';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  constructor(private userService:UserService){}
  loginInput : LoginReq={
    username:"",
    password:""
  }

  public login(){
    this.userService.login(this.loginInput).subscribe({
      next: (data) =>{
       console.log("Result from backend",data);
        
      },
      error: (err) =>{
        console.log(err);
        
      }
    })
  }

  register(){
    
  }
  
}
