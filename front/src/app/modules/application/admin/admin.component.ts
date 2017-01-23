import { Component, OnInit } from '@angular/core';
import {AdminService} from "../../../services/admin.service";
import {User} from "../../../model/user";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users:User[];

  constructor(private adminService:AdminService) { }

  ngOnInit() {
    this.adminService.getReportedUser().then(response => { console.debug('users ', response); this.users = response })
  }

  unblock(user:User) {
    this.adminService.unblock(user.id).then(() => user.blocked = false);
  }

  block(user:User) {
    this.adminService.block(user.id).then(() => user.blocked = true);
  }
}
