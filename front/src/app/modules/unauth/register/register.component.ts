import {Component, OnInit, ViewContainerRef} from "@angular/core";
import {Router, ActivatedRoute} from "@angular/router";
import {User} from "../../../model/user";
import {Fetish} from "../../../model/fetish";
import {DatetimeService} from "../../../services/datetime.service";
import {UserService} from "../../../services/user.service";
import {FetishService} from "../../../services/fetish.service";
import {Register} from "../../../model/register";
import {UserType} from "../../../model/userType";
import {UserTypeService} from "../../../services/user-type.service";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ToastsManager} from "ng2-toastr";
import {Constant} from "../../../model/response/constants";
import {Country} from "../../../model/country";
import {Area} from "../../../model/area";
import { overlayConfigFactory} from "angular2-modal";
import { Overlay } from 'angular2-modal';
import {Modal, BSModalContext} from 'angular2-modal/plugins/bootstrap';
import {environment} from "../../../../environments/environment";
import {LoggerService} from "../../../services/logger.service";

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  password: string;
  birthDate: string;
  selectedFetishId: number[] = [];
  types: UserType[];
  terms: boolean;
  // Captcha
  googleKey: string;
  isBot = true;
  loading = false;
  constants: Constant;

  constructor(private route: ActivatedRoute, private datetimeService: DatetimeService, private userService: UserService,
              private localStorageS: CoolLocalStorage, private router: Router, private fetishService: FetishService,
              private typeService: UserTypeService, private toastr: ToastsManager, vcRef: ViewContainerRef, public overlay: Overlay,
              public modal: Modal, private logger:LoggerService) {
    this.toastr.setRootViewContainerRef(vcRef);
    this.googleKey = environment.googleKey;

    if (!environment.production) {
      this.isBot = false;
    }
  }

  ngOnInit() {

    this.user.country = new Country(1);
    this.user.area = new Area(1);
    this.user.userType = new UserType(0);

    // init register
    if (!environment.production) {
      this.user.username = 'test';
      this.user.email = 'jasmine.domina75@gmail.com';
      this.password = 'test';
      this.user.fetishes = [new Fetish(1, "t"), new Fetish(2, "t")];
      this.user.description = "test";
      this.user.userType.id = 1;
    }


    this.route.data.forEach((data:any) => {
      // si les resolvers renvoient une erreur, elles se trouvent dans data
      data.constants instanceof Object ? this.constants = data.constants : this.toastr.error("Erreur de connexion");
    });
  }

  send(valid: boolean) {
    this.logger.log("is register form valid", valid);
    this.logger.log("is register user bot", this.isBot);
    if (valid && !this.isBot) {
      // on set la valeur birthdate avc le format standard
      this.user.birthDate = this.datetimeService.toStandardFormat(this.birthDate);
      // on met à jour la liste des fetishes
      this.user.fetishes = this.fetishService.getFetishListFromIdList(this.selectedFetishId);
      this.loading = true
      this.userService.post(new Register(this.user, this.password))
        .then(response => {
          this.toastr.success('Inscription effectuée, vous allez être redirigé vers le login');
          setTimeout(() => this.router.navigate(['/unauth/home']), 3000);
        })
        .catch(error => {
            this.toastr.error(error);
            this.loading = false;
          }
        )
    }
  }

  captchaEvent($event) {
    this.isBot = false;
  }
}
