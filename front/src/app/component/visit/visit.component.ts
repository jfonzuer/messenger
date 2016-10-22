import { Component, OnInit } from '@angular/core';
import {VisitService} from "../../service/visit.service";
import {Visit} from "../../model/visit";
import {DatetimeService} from "../../service/datetime.service";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-visit',
  templateUrl: 'visit.component.html',
  styleUrls: ['visit.component.css']
})
export class VisitComponent implements OnInit {

  visits:Visit[];

  constructor(private visitService: VisitService, private datetimeService:DatetimeService, private sharedService: SharedService) { }

  ngOnInit() {
    this.visitService.getAll().then(visits => { this.visits = visits; /*this.formatVisits(visits);*/ this.sharedService.refreshUnseenNumberVisits(); });
  }
}
