import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {SharedService} from "../../../../services/shared.service";

@Component({
  selector: 'app-block-user',
  templateUrl: 'block-user.component.html',
  styleUrls: ['block-user.component.css']
})
export class BlockUserComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  constructor(private us:UserService, private sharedService:SharedService) { }

  ngOnInit() {
  }

  unblock(user:User) {
    let currentUser:User = this.sharedService.getCurrentUser();
    this.us.unblockUser(user)
      .then(users => {
        currentUser.blockedUsers = users;
        this.sharedService.refreshUser(currentUser);
      })
      .catch(error => this.errorEmitter.emit(error));
  }
}
