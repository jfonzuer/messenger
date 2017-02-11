import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {User} from "../../../model/user";
import {ActivatedRoute, Params} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {environment} from "../../../../environments/environment";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-user-view',
  templateUrl: 'user-view.component.html',
  styleUrls: ['user-view.component.css']
})
export class UserViewComponent implements OnInit {
  user:User;
  loading:boolean = true;
  uploadImageUrl:string;

  constructor(private route:ActivatedRoute, private userService:UserService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
      console.log(this.user);
    });
  }

  report(id:number) {
    this.userService.report(id).then(response => this.toastr.success(response.message)).catch(e => this.toastr.error(e));
  }
}
