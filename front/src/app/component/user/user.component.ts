import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {AuthenticationService} from "../../service/authentication.service";
import {User} from "../../model/user";
import { LocalStorageService } from 'angular-2-local-storage';
import {SharedService} from "../../service/shared.service";
import {Localization} from "../../model/localization";
import {LocalizationService} from "../../service/localization.service";
import {FetishService} from "../../service/fetish.service";
import {Fetish} from "../../model/fetish";

@Component({
  selector: 'app-user',
  templateUrl: 'user.component.html',
  styleUrls: ['user.component.css']
})
export class UserComponent implements OnInit {

  user:User;
  error:string;
  success:string;
  localizations:Localization[];
  fetishes: Fetish[];
  selectedFetishId:number[] = [];

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: LocalStorageService,
              private sharedService:SharedService, private localizationService:LocalizationService, private fetishService:FetishService) { }

  ngOnInit() {
    this.user = <User> this.localStorageService.get('user');
    console.log(this.user);

    // si les localisations ne sont pas déja enregistrées en local
    if (this.sharedService.getLocalizations() == null) {
      this.localizationService.getAll().then(localizations => { this.localizations = localizations; console.log(this.localizations); }).catch(error => this.error = error);
    }

    this.fetishService.getAll().then(fetishes => {
      this.fetishes = fetishes;

      // on initialise la liste des id des pratiques
      for (let fetish of this.user.fetishes) {
        this.selectedFetishId.push(fetish.id);
      }
    }).catch(error => this.error = error);
  }

  updateCheckedFetishes(fetish, event) {
    event.target.checked ? this.selectedFetishId.push(fetish.id) : this.selectedFetishId.splice(this.selectedFetishId.indexOf(fetish.id), 1);
    this.selectedFetishId.sort();
    console.log(this.selectedFetishId);
  }

  send(valid:boolean) {
    if (valid) {
      // on met à jour la liste des fetishes
      this.user.fetishes = [];
      for (let id of this.selectedFetishId) {
        this.user.fetishes.push(new Fetish(id, ""));
      }
      console.log(this.user);
      this.userService.updateProfile(this.user).then(user => { this.user = user as User; this.localStorageService.set('user', user); this.success = "Mise à jour effectuée"; setTimeout(() => this.success = "", 2000); }).catch(error => this.error = error);
    }
  }
}
