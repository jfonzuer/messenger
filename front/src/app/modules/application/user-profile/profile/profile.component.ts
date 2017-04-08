import {Component, OnInit, Input, ViewContainerRef} from "@angular/core";
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {FetishService} from "../../../../services/fetish.service";
import {SharedService} from "../../../../services/shared.service";
import {ToastsManager} from "ng2-toastr";
import {Constant} from "../../../../model/response/constants";

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {

  @Input() user:User;
  @Input() constants:Constant;
  @Input() show:boolean = false;
  loading:boolean = false;

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

  send() {
    this.loading = true;
    // on met à jour la liste des fetishes
    this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
    this.userService.updateProfile(this.user).then(user => {
      this.loading = false;
      this.user = user; this.sharedService.refreshUser(user);
      this.toastr.success("Mise à jour effectuée");
    }).catch(
      error => {
        this.toastr.error(error)
        this.loading = false;
      }
    );
  }
}
