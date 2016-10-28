import { Component, OnInit } from '@angular/core';
import {Visit} from "../../model/visit";
import {VisitService} from "../../services/visit.service";
import {DatetimeService} from "../../services/datetime.service";
import {SharedService} from "../../services/shared.service";

@Component({
  selector: 'app-user-visits',
  templateUrl: './user-visits.component.html',
  styleUrls: ['./user-visits.component.css']
})
export class UserVisitsComponent implements OnInit {

  visits:Visit[];

  constructor(private visitService: VisitService, private datetimeService:DatetimeService, private sharedService: SharedService) { }

  ngOnInit() {
    this.visitService.getAll().then(visits => { this.visits = visits; /*this.formatVisits(visits);*/ this.sharedService.refreshUnseenNumberVisits(); });
  }
}
