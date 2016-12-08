import {Component, OnInit, Input} from '@angular/core';
import {Localization} from "../../../model/localization";
import {User} from "../../../model/user";
import {Fetish} from "../../../model/fetish";
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

  constructor(private route:ActivatedRoute) { }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
      data.localizations instanceof Array ? this.localizations = data.localizations : this.error = data.localizations;
      data.fetishes instanceof Array ? this.fetishes = data.fetishes : this.error = data.fetishes;
    });
  }

  errorListener(error:string) : void {
    this.error = error;
    setTimeout(() => this.error = "", 2000);
  }

  successListener(success:string) : void {
    this.success = success;
    setTimeout(() => this.success = "", 2000);
  }

}
