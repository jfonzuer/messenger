import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import Alert = webdriver.Alert;
import {Alerts} from "../../../../model/alerts";
import {SharedService} from "../../../../services/shared.service";

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

  constructor(private userService:UserService, private sharedService:SharedService) { }

  ngOnInit() {
    console.log(this.user);
    console.log(this.user.notifyMessage);
    console.log("prout " + this.user.notifyVisit);
    this.alerts.notifyMessage = this.user.notifyMessage;
    this.alerts.notifyVisit = this.user.notifyVisit;
  }

  send() {
    // si les préférences ont changé
    if (this.alerts.notifyMessage != this.user.notifyMessage || this.alerts.notifyVisit != this.user.notifyVisit) {
      this.userService.updateAlerts(this.alerts).then(response =>  { this.successEmitter.emit(response.message); this.setAlerts(this.alerts); }).catch(error => this.errorEmitter.emit(error))
    }
  }

  private setAlerts(alerts:Alerts) {
    this.user.notifyMessage = alerts.notifyMessage;
    this.user.notifyVisit = alerts.notifyVisit;
    this.sharedService.refreshUser(this.user);
  }
}
