import {Component, OnInit} from "@angular/core";
import {SharedService} from "../../../services/shared.service";

@Component({
  selector: 'app-heading',
  templateUrl: 'heading.component.html',
  styleUrls: ['heading.component.css']
})
export class HeadingComponent implements OnInit {

  title:string;

  constructor(private sharedService: SharedService) { }

  ngOnInit() {
    let user = this.sharedService.getCurrentUser();
    user.userType.name == 'Soumis' ? this.title = 'Rencontrez une femme Dominatrice' : this.title = 'Rencontrez un homme soumis';
  }

}
