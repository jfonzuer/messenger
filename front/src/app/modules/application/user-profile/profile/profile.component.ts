import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {User} from "../../../../model/user";
import {Localization} from "../../../../model/localization";
import {Fetish} from "../../../../model/fetish";
import {UserService} from "../../../../services/user.service";
import {FetishService} from "../../../../services/fetish.service";
import {SharedService} from "../../../../services/shared.service";

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

  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  error:string;
  success:string;
  selectedFetishId:number[] = [];
  title:string;

  constructor(private userService:UserService, private fetishService:FetishService, private sharedService:SharedService) { }

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
      this.userService.updateProfile(this.user).then(user => { this.user = user; this.sharedService.refreshUser(user); this.successEmitter.emit("Mise à jour effectuée"); }).catch(error => this.errorEmitter.emit(error));
    }
  }
}
