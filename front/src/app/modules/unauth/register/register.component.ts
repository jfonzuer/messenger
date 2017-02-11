import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Router, ActivatedRoute} from "@angular/router";
import {PasswordConfirmation} from "../../../model/passwordConfirmation";
import {User} from "../../../model/user";
import {Localization} from "../../../model/localization";
import {Fetish} from "../../../model/fetish";
import {DatetimeService} from "../../../services/datetime.service";
import {UserService} from "../../../services/user.service";
import {LocalizationService} from "../../../services/localization.service";
import {FetishService} from "../../../services/fetish.service";
import {Register} from "../../../model/register";
import {UserType} from "../../../model/userType";
import {UserTypeService} from "../../../services/user-type.service";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css']
})
export class RegisterComponent implements OnInit {

  user:User = new User();
  passwordRegister:PasswordConfirmation = new PasswordConfirmation();
  birthDate:string = '29/03/1992';
  localizations:Localization[];
  fetishes: Fetish[];
  selectedFetishId:number[] = [];
  types: UserType[];

  constructor(private route:ActivatedRoute, private datetimeService: DatetimeService, private userService: UserService, private localStorageS: CoolLocalStorage,
              private router:Router, private localizationService:LocalizationService, private fetishService:FetishService, private typeService:UserTypeService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.user.localization = new Localization(0);
    this.user.userType = new UserType(0);

    // init register
    this.user.username = 'test';
    this.user.email = 'pgiraultmatz@gmail.com';
    this.passwordRegister.password = 'test';
    this.passwordRegister.confirmation = 'test';
    this.user.fetishes = [new Fetish(1, "t"), new Fetish(2, "t")];
    this.user.localization.id = 1;
    this.user.description = "test";
    this.user.userType.id = 1;

    this.route.data.forEach((data:any) => {
      // si les resolvers renvoient une erreur, elles se trouvent dans data
      data.userTypes instanceof Array ? this.types = data.userTypes : this.toastr.error(data.userTypes);
      data.localizations instanceof Array ? this.localizations = data.localizations : this.toastr.error(data.localizations);
      data.fetishes instanceof Array ? this.fetishes = data.fetishes : this.toastr.error(data.fetishes);
    });
  }

  updateCheckedFetishes(fetish, event) {
    this.fetishService.updateCheckedFetishes(fetish, event, this.selectedFetishId);
  }

  send(valid:boolean) {
    if (valid) {
      if (this.passwordRegister.password == this.passwordRegister.confirmation) {
        // on set la valeur birthdate avc le format standard
        this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
        // on met à jour la liste des fetishes
        this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
        this.userService.post(new Register(this.user, this.passwordRegister))
          .then(response => { this.toastr.success("Inscription effectuée, vous allez être redirigé vers le login"); setTimeout(() => this.router.navigate(['/unauth/login']), 3000); })
          .catch(error => this.toastr.error(error))
      }
      else {
        this.toastr.error("Les mots de passe doivent être identiques");
      }
    }
  }
}
