import { Component, OnInit } from '@angular/core';
import {SharedService} from "../../service/shared.service";
import {User} from "../../model/user";
import {AuthenticationService} from "../../service/authentication.service";
import {UserService} from "../../service/user.service";
import {DatetimeService} from "../../service/datetime.service";
import { LocalStorageService } from 'angular-2-local-storage';
import {PasswordConfirmation} from "../../model/passwordConfirmation";

@Component({
  selector: 'app-parameters',
  templateUrl: 'parameters.component.html',
  styleUrls: ['parameters.component.css']
})
export class ParametersComponent implements OnInit {

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
      this.userService.updateInformations(this.user).then(user => { this.user = user; this.localStorageService.set('user', user); }).catch(error => { this.error = error; });
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
