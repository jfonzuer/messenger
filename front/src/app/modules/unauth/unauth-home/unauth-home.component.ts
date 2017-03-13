import {Component, OnInit, ViewContainerRef, OnDestroy} from '@angular/core';
import {UnauthUserService} from "../../../services/unauth-user.service";
import {User} from "../../../model/user";
import {ToastsManager} from "ng2-toastr";
import {environment} from "../../../../environments/environment";
import {ActivatedRoute, Params} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";

@Component({
  selector: 'app-unauth-home',
  templateUrl: 'unauth-home.component.html',
  styleUrls: ['unauth-home.component.css']
})
export class UnauthHomeComponent implements OnInit {

  dominas:User[];
  private uploadImageUrl;

  constructor(private route:ActivatedRoute, private authenticationS:AuthenticationService, private unauthUserS: UnauthUserService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef)
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
        if (params['token']) {
          console.debug('token', params['token']);
          let token:string = params['token'];
          this.authenticationS.sendActivationToken(token).then(() =>
            this.toastr.success("Votre compte a bien été activé")
          );
        }
    })

    this.unauthUserS.getLastRegisteredDominas()
      .then(dominas => {
        console.log(dominas);
        this.dominas = dominas as User[];
      })
  }
}
