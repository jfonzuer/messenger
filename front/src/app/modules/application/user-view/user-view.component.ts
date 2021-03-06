import {Component, Input, OnInit, ViewContainerRef} from "@angular/core";
import {User} from "../../../model/user";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {environment} from "../../../../environments/environment";
import {ToastsManager} from "ng2-toastr";
import {Image} from "../../../model/image";

@Component({
  selector: 'app-user-view',
  templateUrl: 'user-view.component.html',
  styleUrls: ['user-view.component.css']
})
export class UserViewComponent implements OnInit {
  user:User;
  loading:boolean = true;
  uploadImageUrl = environment.uploadImageUrl;

  selectedImage: Image;

  constructor(private route:ActivatedRoute, private userService:UserService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
    });
  }

  report(id:number) {
    this.userService.report(id).then(response => this.toastr.success(response.message)).catch(e => this.toastr.error(e));
  }

  closeImage() {
    this.selectedImage = null;
  }
}
