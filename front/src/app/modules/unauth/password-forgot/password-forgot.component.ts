import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";

@Component({
  selector: 'app-password-forgot',
  templateUrl: 'password-forgot.component.html',
  styleUrls: ['password-forgot.component.css']
})
export class PasswordForgotComponent implements OnInit {

  email:string;
  error:string;
  success:string;

  constructor(private authenticationService:AuthenticationService, private router:Router) { }

  ngOnInit() {
    this.email = 'pgiraultmatz@gmail.com';
  }

  send(valid:boolean) {
    if (valid) {
      this.success = "Envoi du mail ...";
      this.authenticationService.resetPasswordByEmail(this.email).then(() => {
        this.success = "Un email vous a été envoyé pour réinitialiser le mot de passe, vous allez être redirigé vers le login";
        setTimeout(this.router.navigate(['/unauth/login']), 2000);
      })
        .catch(error => { this.error = error; setTimeout(this.error = "", 2000)});
    }
  }


}
