import {Component, OnInit, EventEmitter, Output, Input, ViewContainerRef} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {SharedService} from "../../../../services/shared.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-block-user',
  templateUrl: 'block-user.component.html',
  styleUrls: ['block-user.component.css']
})
export class BlockUserComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  loading:boolean = false;

  constructor(private us:UserService, private sharedService:SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
  }

  unblock(user:User) {
    let currentUser:User = this.sharedService.getCurrentUser();
    this.loading = true;
    this.us.unblockUser(user)
      .then(users => {
        this.loading = false;
        this.user.blockedUsers = users;
        currentUser.blockedUsers = users;
        this.sharedService.refreshUser(currentUser);
        this.toastr.success("L'utilisateur a bien été débloqué");
      })
      .catch(error => {
        this.loading = false;
        this.toastr.error(error)
      });
  }
}
