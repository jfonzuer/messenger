import {Component, OnInit, Input, ViewContainerRef} from "@angular/core";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";
import {SharedService} from "../../../../services/shared.service";
import {DatetimeService} from "../../../../services/datetime.service";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";
import {CoolLocalStorage} from "angular2-cool-storage";

@Component({
  selector: 'app-information',
  templateUrl: 'information.component.html',
  styleUrls: ['information.component.css']
})
export class InformationComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  birthDate:string;
  email:string;
  loading:boolean;

  constructor(private localStorageService: CoolLocalStorage, private userService: UserService, private router:Router, private sharedService:SharedService, private datetimeService:DatetimeService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.birthDate = this.datetimeService.toFrFormat(this.user.birthDate);
  }

  updateInformation() {
    this.loading = true;
    this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
    this.userService.updateInformations(this.user)
      .then(update => {
        this.loading = false;
        let currentUser = this.sharedService.getCurrentUser();
        this.toastr.success("Mise à jour effectuée");

        // si l'utilisateur a changé d'email
        if (update.user.email !== currentUser.email) {
          this.localStorageService.setObject("token", update.token);
        }
        this.user = update.user; this.sharedService.refreshUser(update.user);
      })
      .catch(error => {
        this.loading = false;
        this.toastr.error(error)
      });
  }
}
