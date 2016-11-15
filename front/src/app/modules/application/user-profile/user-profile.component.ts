import { Component, OnInit } from '@angular/core';
import {LocalStorageService} from 'angular-2-local-storage';
import {Localization} from "../../../model/localization";
import {User} from "../../../model/user";
import {Fetish} from "../../../model/fetish";
import {AuthenticationService} from "../../../services/authentication.service";
import {UserService} from "../../../services/user.service";
import {SharedService} from "../../../services/shared.service";
import {LocalizationService} from "../../../services/localization.service";
import {FetishService} from "../../../services/fetish.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: 'user-profile.component.html',
  styleUrls: ['user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user:User;
  error:string;
  success:string;
  localizations:Localization[];
  fetishes: Fetish[];
  selectedFetishId:number[] = [];
  title:string;

  constructor(private route:ActivatedRoute, private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: LocalStorageService,
              private sharedService:SharedService, private localizationService:LocalizationService, private fetishService:FetishService) { }

  ngOnInit() {
    this.user = <User> this.sharedService.getCurrentUser();
    this.sharedService.isDomina() ? this.title = 'Mon profil de dominatrice' : this.title = 'Mon profil de soumis';

    this.route.data.forEach((data:any) => {
      data.localizations instanceof Array ? this.localizations = data.localizations : this.error = data.localizations;
      data.fetishes instanceof Array ? this.fetishes = data.fetishes : this.error = data.fetishes;

      this.selectedFetishId = this.fetishService.initIdList(this.user.fetishes);
    });
  }

  updateCheckedFetishes(fetish, event) {
    this.fetishService.updateCheckedFetishes(fetish, event, this.selectedFetishId);
  }

  send(valid:boolean) {
    if (valid) {
      // on met à jour la liste des fetishes
      this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
      this.userService.updateProfile(this.user).then(user => { this.user = user; this.localStorageService.set('user', user); this.sharedService.refreshUser(user); this.success = "Mise à jour effectuée"; setTimeout(() => this.success = "", 2000); }).catch(error => this.error = error);
    }
  }
}
