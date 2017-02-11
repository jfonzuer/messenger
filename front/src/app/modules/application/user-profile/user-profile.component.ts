import {Component, OnInit, Input, ViewContainerRef} from '@angular/core';
import {Localization} from "../../../model/localization";
import {User} from "../../../model/user";
import {Fetish} from "../../../model/fetish";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-user-profile',
  templateUrl: 'user-profile.component.html',
  styleUrls: ['user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user:User;
  localizations:Localization[];
  fetishes: Fetish[];

  constructor(private route:ActivatedRoute, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
      // si data n'est pas un array, il contient alors une erreur
      data.localizations instanceof Array ? this.localizations = data.localizations : this.toastr.error(data.localizations);
      data.fetishes instanceof Array ? this.fetishes = data.fetishes : this.toastr.error(data.fetishes);
    });
  }
}
