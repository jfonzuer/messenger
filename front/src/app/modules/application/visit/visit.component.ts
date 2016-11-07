import { Component, OnInit } from '@angular/core';
import {Visit} from "../../../model/visit";
import {VisitService} from "../../../services/visit.service";
import {DatetimeService} from "../../../services/datetime.service";
import {SharedService} from "../../../services/shared.service";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-visit',
  templateUrl: 'visit.component.html',
  styleUrls: ['visit.component.css']
})
export class VisitComponent implements OnInit {

  visits:Visit[];
  uploadUrl:string;

  constructor(private visitService: VisitService, private datetimeService:DatetimeService, private sharedService: SharedService) {
    this.uploadUrl = environment.uploadUrl;
  }

  ngOnInit() {
    this.visitService.getAll().then(visits => { this.visits = visits; this.datetimeService.formatVisits(visits); this.sharedService.refreshUnseenNumberVisits(); });
  }
}
