import {Component, OnInit, EventEmitter, Output, Input, ViewContainerRef} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {Alerts} from "../../../../model/alerts";
import {SharedService} from "../../../../services/shared.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-alerts',
  templateUrl: 'alerts.component.html',
  styleUrls: ['alerts.component.css']
})
export class AlertsComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  alerts:Alerts = new Alerts();
  loading:boolean = false;

  constructor(private userService :UserService, private sharedService:SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.alerts.notifyMessage = this.user.notifyMessage;
    this.alerts.notifyVisit = this.user.notifyVisit;
    this.alerts.notifyOther = this.user.notifyOther;
  }

  send() {
    // si les préférences ont changé
    if (this.alerts.notifyMessage != this.user.notifyMessage || this.alerts.notifyVisit != this.user.notifyVisit) {
      this.loading = true;
      this.userService.updateAlerts(this.alerts)
        .then(response => {
          this.loading = false;
          this.toastr.success(response.message);
          this.setAlerts(this.alerts);
        })
        .catch(error => {
          this.loading = false;
          this.toastr.error(error)
        })
    }
  }

  private setAlerts(alerts:Alerts) {
    this.user.notifyMessage = alerts.notifyMessage;
    this.user.notifyVisit = alerts.notifyVisit;
    this.user.notifyOther = alerts.notifyOther;
    this.sharedService.refreshUser(this.user);
  }
}
