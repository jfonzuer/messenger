import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Visit} from "../../../model/visit";
import {VisitService} from "../../../services/visit.service";
import {DatetimeService} from "../../../services/datetime.service";
import {SharedService} from "../../../services/shared.service";
import {environment} from "../../../../environments/environment";
import {ToastsManager} from "ng2-toastr";
import {Pager} from "../../../model/pager";

@Component({
  selector: 'app-visit',
  templateUrl: 'visit.component.html',
  styleUrls: ['visit.component.css']
})
export class VisitComponent implements OnInit {

  visits:Visit[];
  uploadImageUrl:string;
  pager:Pager;

  constructor(private visitService: VisitService, private datetimeService:DatetimeService, private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.getVisits();
    this.visits = [];
  }

  getVisits() {
    this.visitService.getVisits(this.pager).then(response => {
      this.visits = this.visits.concat(response.content);
      this.pager = new Pager(response.number, response.last, response.size, 10);

      this.sharedService.refreshUnseenNumberVisits();

    })/*.catch(error => {
      this.toastr.error(error);
    });*/
  }

  scrollDown() {
    this.pager.page = this.pager.page + 1;
    this.getVisits();
  }
}
