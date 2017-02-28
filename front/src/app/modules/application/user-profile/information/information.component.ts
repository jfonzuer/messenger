import {Component, OnInit, Input, ViewContainerRef} from "@angular/core";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";
import {SharedService} from "../../../../services/shared.service";
import {DatetimeService} from "../../../../services/datetime.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-information',
  templateUrl: 'information.component.html',
  styleUrls: ['information.component.css']
})
export class InformationComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  birthDate:string;

  constructor(private userService: UserService, private sharedService:SharedService, private datetimeService:DatetimeService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.birthDate = this.datetimeService.toFrFormat(this.user.birthDate);
  }

  updateInformation(valid:boolean) {
    if (valid) {
      this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
      this.userService.updateInformations(this.user).then(user => { this.user = user; this.sharedService.refreshUser(user); }).catch(error => { this.toastr.error(error) });
    }
  }
}
