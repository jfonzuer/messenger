import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {ActivatedRoute, Params} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-user-view',
  templateUrl: 'user-view.component.html',
  styleUrls: ['user-view.component.css']
})
export class UserViewComponent implements OnInit {
  user:User;
  loading:boolean = true;
  uploadImageUrl:string;
  success:string;
  error:string;

  constructor(private route:ActivatedRoute, private userService:UserService) {
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
      console.log(this.user);
    });
  }

  report(id:number) {
    this.userService.report(id).then(response => { this.success = response.message; setTimeout(() => this.success = "", 2000); }).catch(e => this.error = e);
  }
}
