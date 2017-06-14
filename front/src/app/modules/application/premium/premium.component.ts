import {Component, OnInit, ViewContainerRef, Input} from '@angular/core';
import {AuthenticationService} from "../../../services/authentication.service";
import {User} from "../../../model/user";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ActivatedRoute, Params} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {SharedService} from "../../../services/shared.service";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-premium',
  templateUrl: 'premium.component.html',
  styleUrls: ['premium.component.css']
})
export class PremiumComponent implements OnInit {

  user:User;
  isPremium:boolean;
  paymentApiUrl:string;
  @Input() show:boolean;

  constructor(private localStorageService: CoolLocalStorage,private route: ActivatedRoute, private authenticationS:AuthenticationService, private toastr: ToastsManager, vRef: ViewContainerRef, private sharedService: SharedService) {
    this.toastr.setRootViewContainerRef(vRef);
    this.paymentApiUrl = environment.paymentApiUrl;
  }

  ngOnInit() {

    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['state']) {
        let state:string = params['state'];
        if (state == 'success') {
          this.authenticationS
            .getAuthenticatedUser().then(user => { this.user = user; this.sharedService.refreshUser(user);
            this.isPremium = this.sharedService.isPremium();

            if (this.isPremium) {
              this.toastr.success("Félicitations, vous êtes désormais un utilisateur Premium");
            }
            })
            .catch(error => this.toastr.error(error));
        } else if (state == 'error') {
          this.toastr.error("Erreur de lors de l'attribution de votre abonnement, veuillez nous contacter à contact@dominapp.com");
        }
      }
    });

    this.show == null ? this.show = true : null;

    this.isPremium = this.sharedService.isPremium();
    this.user = this.sharedService.getCurrentUser();
  }
}
