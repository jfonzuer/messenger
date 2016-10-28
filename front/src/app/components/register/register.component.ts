import { Component, OnInit } from '@angular/core';
import {LocalStorageService} from 'angular-2-local-storage';
import {PasswordConfirmation} from "../../model/passwordConfirmation";
import {User} from "../../model/user";
import {Localization} from "../../model/localization";
import {Fetish} from "../../model/fetish";
import {DatetimeService} from "../../services/datetime.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {FetishService} from "../../services/fetish.service";
import {LocalizationService} from "../../services/localization.service";
import {Register} from "../../model/register";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
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

  constructor(private datetimeService: DatetimeService, private userService: UserService, private localStorageService: LocalStorageService,
              private router:Router, private localizationService:LocalizationService, private fetishService:FetishService) { }

  ngOnInit() {
    this.user.localization = new Localization();

    // init register
    this.user.username = 'test';
    this.user.email = 'pgiraultmatz@gmail.com';
    this.passwordRegister.password = 'test';
    this.passwordRegister.confirmation = 'test';
    this.user.birthDate = '29/03/1992';
    this.user.fetishes = [new Fetish(1, "t"), new Fetish(2, "t")];
    this.user.localization.id = 1;
    this.user.description = "test";

    this.localStorageService.get('localizations') != null ?
      this.localizations = <Localization[]> this.localStorageService.get('localizations') :
      this.localizationService.getAll().then(localizations => { this.localizations = localizations; this.localStorageService.set('localizations', localizations)}).catch(error => this.error = error);


    this.localStorageService.get('fetishes') != null ?
      this.fetishes = <Fetish[]> this.localStorageService.get('fetishes') :
      this.fetishService.getAll().then(fetishes => { this.fetishes = fetishes; this.selectedFetishId = this.fetishService.initIdList(fetishes); }).catch(error => this.error = error);
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
        this.userService.post(new Register(this.user, this.passwordRegister)).then(response => { this.success = "Inscription effectuée, vous allez être redirigé vers le login"; setTimeout(() => this.router.navigate(['/unauth/login']), 3000); }).catch(error => this.error = error);
      }
      else {
        this.error = "Les mots de passe doivent être identiques"; setTimeout(() => this.error = "", 2000);
      }
    }
  }
}
