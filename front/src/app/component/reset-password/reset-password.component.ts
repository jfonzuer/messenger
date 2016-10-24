import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {ResetPassword} from "../../model/resetPassword";
import {PasswordConfirmation} from "../../model/passwordConfirmation";

@Component({
  selector: 'app-reset-password',
  templateUrl: 'reset-password.component.html',
  styleUrls: ['reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  error:string;
  success:string;
  userId:number;
  token:string;
  passwordConfirmation:PasswordConfirmation = new PasswordConfirmation();

  constructor(private route:ActivatedRoute, private router:Router, private userService: UserService) { }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['id'] && params['token']) {
        this.userId = +params['id'];// (+) converts string 'id' to a number
        this.token = params['token'];

      }
      else {
        this.router.navigateByUrl('login');
      }
    });
  }

  send(valid:boolean) {
    if (valid) {
      if (this.passwordConfirmation.password == this.passwordConfirmation.confirmation) {
        console.log(this.passwordConfirmation);
        console.log(new ResetPassword(this.passwordConfirmation, this.token, this.userId));
        //this.userService.resetPassword(this.passwordConfirmation)
        this.userService.resetPassword(new ResetPassword(this.passwordConfirmation, this.token, this.userId))
          .then(() => { this.success = "Le mot de passe a été modifié, vous allez être redirigé vers la page de login"; setTimeout(this.router.navigateByUrl('login'), 2000) })
          .catch(error => this.error = error);
      }
      else {
        this.error = "Les mots de passe doivent être identiques"; setTimeout(() => this.error = "", 2000);
      }
    }
  }
}
