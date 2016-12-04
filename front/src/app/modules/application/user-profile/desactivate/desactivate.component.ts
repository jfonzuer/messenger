import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {Desactivate} from "../../../../model/desactivate";
import {Router} from "@angular/router";
import {SharedService} from "../../../../services/shared.service";

@Component({
  selector: 'app-desactivate',
  templateUrl: 'desactivate.component.html',
  styleUrls: ['desactivate.component.css']
})

export class DesactivateComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();
  desactivate:Desactivate = new Desactivate();

  constructor(private userService:UserService, private router:Router, private sharedService:SharedService) { }

  ngOnInit() {
  }

  send() {
    // if we desactivate
    if (!this.desactivate.desactivate) {
      this.userService.desactivate(this.desactivate).then(response => {
        this.successEmitter.emit(response.message);
        this.sharedService.desactivate();
        this.router.navigate(['../../unauth/desactivate']);
      }).catch(error => this.errorEmitter.emit(error));
    }
  }
}
