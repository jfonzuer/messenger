import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import {User} from "../../../model/user";
import {Localization} from "../../../model/localization";
import {Fetish} from "../../../model/fetish";
import {AuthenticationService} from "../../../service/authentication.service";
import {UserService} from "../../../service/user.service";
import {SharedService} from "../../../service/shared.service";
import {LocalizationService} from "../../../service/localization.service";
import {FetishService} from "../../../service/fetish.service";


@Component({
  selector: 'app-user-informations',
  templateUrl: 'user-informations.component.html',
  styleUrls: ['user-informations.component.css']
})
export class UserInformationsComponent implements OnInit {
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
      // on met à jour la liste des fetishes
      this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
      this.userService.updateProfile(this.user).then(user => { this.user = user as User; this.localStorageService.set('user', user); this.success = "Mise à jour effectuée"; setTimeout(() => this.success = "", 2000); }).catch(error => this.error = error);
    }
  }
}
