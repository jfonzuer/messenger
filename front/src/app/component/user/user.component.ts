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

  options:string[];
  optionsMap:any;
  selectedFetishId:number[] = [];


  optionsChecked = [];

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: LocalStorageService,
              private sharedService:SharedService, private localizationService:LocalizationService, private fetishService:FetishService) { }

  ngOnInit() {
    this.user = <User> this.localStorageService.get('user');
    //this.authenticationService.getAuthenticatedUser().then(user => this.user = user).catch(error => this.error = error);
    console.log(this.user);

    if (this.sharedService.getLocalizations() == null) {
      this.localizationService.getAll().then(localizations => { this.localizations = localizations; console.log(this.localizations); }).catch(error => this.error = error);

      this.fetishService.getAll().then(fetishes => {
        this.fetishes = fetishes;
        console.log(fetishes);

        this.options = ['OptionA', 'OptionB', 'OptionC'];
        this.optionsMap = {
          OptionA: false,
          OptionB: false,
          OptionC: false,
        };

        for (let fetish of this.user.fetishes) {
          this.selectedFetishId.push(fetish.id);
        }
        console.log(this.selectedFetishId);

        /*

        this.fetishes.forEach((fetishes, index) => {

          let isPresent = false;
          this.user.fetishes.forEach((f, i) => {
            if (fetishes.id == f.id) {
              isPresent = true;
              this.optionsMap.push(f);
            }
          });
          //this.optionsMap.push(isPresent);
        });
        console.log(this.optionsMap);
        console.log(this.optionsMap.indexOf(this.fetishes.pop()));
        */
      }).catch(error => this.error = error);
    }
  }

  updateCheckedOptions(option, event) {
    this.optionsMap[option] = event.target.checked;
    console.log(this.optionsMap);
  }

  updateCheckedFetishes(fetish, event) {
    console.log(fetish);
    console.log(event.target.checked);
    console.log(this.selectedFetishId.indexOf(fetish.id));
    if (event.target.checked)
    this.selectedFetishId.splice(this.selectedFetishId.indexOf(fetish.id), 1);
    console.log(this.selectedFetishId);
    //this.optionsMap[option] = event.target.checked;
    //console.log(this.optionsMap);
  }

  send(valid:boolean) {
    if (valid) {
      console.log(this.user);
      this.userService.updateProfile(this.user).then(user => { this.user = user as User; this.localStorageService.set('user', user); }).catch(error => this.error = error);
    }
  }
}
