import { Component, OnInit } from '@angular/core';
import {LocalStorageService} from 'angular-2-local-storage';
import {Router, ActivatedRoute} from "@angular/router";
import {PasswordConfirmation} from "../../../model/passwordConfirmation";
import {User} from "../../../model/user";
import {Localization} from "../../../model/localization";
import {Fetish} from "../../../model/fetish";
import {DatetimeService} from "../../../services/datetime.service";
import {UserService} from "../../../services/user.service";
import {LocalizationService} from "../../../services/localization.service";
import {FetishService} from "../../../services/fetish.service";
import {Register} from "../../../model/register";
import {UserType} from "../../../model/userType";
import {UserTypeService} from "../../../services/user-type.service";

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css']
})
export class RegisterComponent implements OnInit {

  user:User = new User();
  passwordRegister:PasswordConfirmation = new PasswordConfirmation();
  birthDate:string = '29/03/1992';
  error:string;
  success:string;
  localizations:Localization[];
  fetishes: Fetish[];
  selectedFetishId:number[] = [];
  types: UserType[];

  constructor(private route:ActivatedRoute, private datetimeService: DatetimeService, private userService: UserService, private localStorageS: LocalStorageService,
              private router:Router, private localizationService:LocalizationService, private fetishService:FetishService, private typeService:UserTypeService) { }

  ngOnInit() {
    this.user.localization = new Localization();
    this.user.userType = new UserType();

    // init register
    this.user.username = 'test';
    this.user.email = 'pgiraultmatz@gmail.com';
    this.passwordRegister.password = 'test';
    this.passwordRegister.confirmation = 'test';
    this.user.fetishes = [new Fetish(1, "t"), new Fetish(2, "t")];
    this.user.localization.id = 1;
    this.user.description = "test";
    this.user.userType.id = 1;

    this.route.data.forEach((data:any) => {
      data.userTypes instanceof Array ? this.types = data.userTypes : this.error = data.userTypes;
      data.localizations instanceof Array ? this.localizations = data.localizations : this.error = data.localizations;
      data.fetishes instanceof Array ? this.fetishes = data.fetishes : this.error = data.fetishes;
    });
  }

  updateCheckedFetishes(fetish, event) {
    this.fetishService.updateCheckedFetishes(fetish, event, this.selectedFetishId);
  }

  send(valid:boolean) {
    if (valid) {
      if (this.passwordRegister.password == this.passwordRegister.confirmation) {
        // on set la valeur birthdate avc le format standard
        this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
        // on met à jour la liste des fetishes
        this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
        this.userService.post(new Register(this.user, this.passwordRegister))
          .then(response => { this.success = "Inscription effectuée, vous allez être redirigé vers le login"; setTimeout(() => this.router.navigate(['/unauth/login']), 3000); })
          .catch(error => { this.error = error; setTimeout(() => this.error = "", 2000); })
      }
      else {
        this.error = "Les mots de passe doivent être identiques"; setTimeout(() => this.error = "", 2000);
      }
    }
  }
}
