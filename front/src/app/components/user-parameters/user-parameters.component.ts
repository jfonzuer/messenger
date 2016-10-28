import { Component, OnInit } from '@angular/core';
import {LocalStorageService} from 'angular-2-local-storage';
import {User} from "../../model/user";
import {PasswordConfirmation} from "../../model/passwordConfirmation";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {SharedService} from "../../services/shared.service";
import {DatetimeService} from "../../services/datetime.service";

@Component({
  selector: 'app-user-parameters',
  templateUrl: './user-parameters.component.html',
  styleUrls: ['./user-parameters.component.css']
})
export class UserParametersComponent implements OnInit {

  user:User;
  birthDate:string;
  passwordConfirmation: PasswordConfirmation = new PasswordConfirmation();
  error:string;
  success:string;

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: LocalStorageService,
              private sharedService:SharedService, private datetimeService:DatetimeService) { }


  ngOnInit() {
    this.user = <User> this.localStorageService.get('user');
    this.birthDate = this.datetimeService.toFrFormat(this.user.birthDate);
  }

  updateInformation(valid:boolean) {
    if (valid) {
      this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
      this.userService.updateInformations(this.user).then(user => { this.user = user; this.localStorageService.set('user', user); this.sharedService.refreshUser(user); }).catch(error => { this.error = error; });
    }
  }

  updatePassword(valid:boolean) {
    if (valid) {
      if (this.passwordConfirmation.password == this.passwordConfirmation.confirmation) {
        this.userService.updatePassword(this.passwordConfirmation).then(() => {
          this.success = "Le mot de passe a été modifié avec succés.";
          setTimeout(() => this.success = "", 2000);
          this.authenticationService.refreshToken().then(response => { this.localStorageService.set('token', response.token) }).catch(error => this.error = error);
        })
      } else {
        this.error = "Les mots de passe doivent être identiques"; setTimeout(() => this.error = "", 2000);
      }
    }
  }
}
