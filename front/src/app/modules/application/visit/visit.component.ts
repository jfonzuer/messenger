import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Visit} from "../../../model/visit";
import {VisitService} from "../../../services/visit.service";
import {DatetimeService} from "../../../services/datetime.service";
import {SharedService} from "../../../services/shared.service";
import {environment} from "../../../../environments/environment";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-visit',
  templateUrl: 'visit.component.html',
  styleUrls: ['visit.component.css']
})
export class VisitComponent implements OnInit {

  visits:Visit[];
  uploadImageUrl:string;

  constructor(private visitService: VisitService, private datetimeService:DatetimeService, private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);

  }

  ngOnInit() {
    this.visitService.getAll().then(visits => {
      this.visits = visits; this.datetimeService.formatVisits(visits); this.sharedService.refreshUnseenNumberVisits();
    }).catch(error => {
      this.toastr.error(error);
    });
  }
}
