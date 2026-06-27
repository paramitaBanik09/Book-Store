import { Component } from '@angular/core';
import { User } from '../user.ts/user.ts-module';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.ts';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

  constructor(private userService:UserService){}

  user:User={
    firstName:"",
    lastName:"",
    email:"",
    password:""
  }
  register(){
    this.userService.register(this.user).subscribe({
      next: (data)=>{
        console.log(data)
      },
      error: (err)=>{
        console.log(err);
        
      }
    })
  }
}
