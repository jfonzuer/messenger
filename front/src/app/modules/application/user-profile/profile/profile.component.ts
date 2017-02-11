import {Component, OnInit, Input, EventEmitter, Output, ViewContainerRef} from '@angular/core';
import {User} from "../../../../model/user";
import {Localization} from "../../../../model/localization";
import {Fetish} from "../../../../model/fetish";
import {UserService} from "../../../../services/user.service";
import {FetishService} from "../../../../services/fetish.service";
import {SharedService} from "../../../../services/shared.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {

  @Input() user:User;
  @Input() localizations:Localization[];
  @Input() fetishes: Fetish[];
  @Input() show:boolean = false;

  selectedFetishId:number[] = [];
  title:string;

  constructor(private userService:UserService, private fetishService:FetishService, private sharedService:SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.selectedFetishId = this.fetishService.initIdList(this.user.fetishes);
  }


  updateCheckedFetishes(fetish, event) {
    this.fetishService.updateCheckedFetishes(fetish, event, this.selectedFetishId);
  }

  send(valid:boolean) {
    if (valid) {
      // on met à jour la liste des fetishes
      this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
      this.userService.updateProfile(this.user).then(user => { this.user = user; this.sharedService.refreshUser(user); this.toastr.success("Mise à jour effectuée"); }).catch(error => this.toastr.error(error));
    }
  }
}
