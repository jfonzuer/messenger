import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {FetishService} from "../../../../services/fetish.service";
import {Fetish} from "../../../../model/fetish";
import {Localization} from "../../../../model/localization";
import {User} from "../../../../model/user";
import {SharedService} from "../../../../services/shared.service";
import {DatetimeService} from "../../../../services/datetime.service";

@Component({
  selector: 'app-information',
  templateUrl: 'information.component.html',
  styleUrls: ['information.component.css']
})
export class InformationComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();
  birthDate:string;

  constructor(private userService: UserService, private sharedService:SharedService, private datetimeService:DatetimeService) { }

  ngOnInit() {
    this.birthDate = this.datetimeService.toFrFormat(this.user.birthDate);
  }

  updateInformation(valid:boolean) {
    if (valid) {
      this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
      this.userService.updateInformations(this.user).then(user => { this.user = user; this.sharedService.refreshUser(user); }).catch(error => { this.errorEmitter.emit(error) });
    }
  }
}
