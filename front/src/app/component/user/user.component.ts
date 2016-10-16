import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {AuthenticationService} from "../../service/authentication.service";
import {User} from "../../model/user";
import { LocalStorageService } from 'angular-2-local-storage';

@Component({
  selector: 'app-user',
  templateUrl: 'user.component.html',
  styleUrls: ['user.component.css']
})
export class UserComponent implements OnInit {

  user:User;
  error:string;
  success:string;
  constructor(private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.user = <User> this.localStorageService.get('user');
    //this.authenticationService.getAuthenticatedUser().then(user => this.user = user).catch(error => this.error = error);
  }

  send(valid:boolean) {
    if (valid) {
      console.log(this.user);
      this.userService.updateProfile(this.user).then(user => { this.user = user as User; this.localStorageService.set('user', user); }).catch(error => this.error = error);
    }
  }

}
