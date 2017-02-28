import {Component, OnInit, ViewContainerRef} from "@angular/core";
import {User} from "../../../model/user";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {Constant} from "../../../model/response/constants";

@Component({
  selector: 'app-user-profile',
  templateUrl: 'user-profile.component.html',
  styleUrls: ['user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user:User;
  constants:Constant;

  constructor(private route:ActivatedRoute, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
      // si data n'est pas un array, il contient alors une erreur
      data.constants instanceof Object ? this.constants = data.constants : this.toastr.error("Erreur de connexion");
    });
  }
}
