import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import Alert = webdriver.Alert;
import {Alerts} from "../../../../model/alerts";

@Component({
  selector: 'app-alerts',
  templateUrl: 'alerts.component.html',
  styleUrls: ['alerts.component.css']
})
export class AlertsComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();
  alerts:Alerts = new Alerts();

  constructor(private userService:UserService) { }

  ngOnInit() {
  }


}
