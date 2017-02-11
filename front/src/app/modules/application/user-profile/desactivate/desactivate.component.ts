import {Component, OnInit, EventEmitter, Output, Input, ViewContainerRef} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {Desactivate} from "../../../../model/desactivate";
import {Router} from "@angular/router";
import {SharedService} from "../../../../services/shared.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-desactivate',
  templateUrl: 'desactivate.component.html',
  styleUrls: ['desactivate.component.css']
})

export class DesactivateComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  desactivate:Desactivate = new Desactivate();

  constructor(private userService:UserService, private router:Router, private sharedService:SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
  }

  send() {
    // if we desactivate
    if (!this.desactivate.desactivate) {
      this.userService.desactivate(this.desactivate).then(response => {
        this.toastr.error(response.message);
        this.sharedService.desactivate();
        this.router.navigate(['../../unauth/desactivate']);
      }).catch(error => this.toastr.error(error));
    }
  }
}
