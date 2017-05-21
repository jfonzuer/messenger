import {Component, OnInit, ViewContainerRef} from "@angular/core";
import {User} from "../../../model/user";
import {UserService} from "../../../services/user.service";
import {SharedService} from "../../../services/shared.service";
import {environment} from "../../../../environments/environment";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent implements OnInit {

  users:User[];
  title:string;
  token:string;
  uploadImageUrl:string;

  constructor(private userService: UserService, private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {
    this.userService.getUsers(null).then(response => {
      this.users = response.content;
    })
    let user = this.sharedService.getCurrentUser();
    user.userType.name == 'Soumis' ? this.title = 'Dernieres dominatrices inscrites' : this.title = 'Derniers soumis inscrits';
  }
}
