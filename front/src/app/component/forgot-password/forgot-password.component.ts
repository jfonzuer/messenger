import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-forgot-password',
  templateUrl: 'forgot-password.component.html',
  styleUrls: ['forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

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
        setTimeout(this.router.navigateByUrl('login'), 2000);
      })
        .catch(error => { this.error = error; setTimeout(this.error = "", 2000)});
    }
  }
}
