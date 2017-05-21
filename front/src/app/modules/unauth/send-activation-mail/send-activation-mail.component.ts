import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {AuthenticationService} from "../../../services/authentication.service";
import {Router} from "@angular/router";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-send-activation-mail',
  templateUrl: './send-activation-mail.component.html',
  styleUrls: ['./send-activation-mail.component.css']
})
export class SendActivationMailComponent implements OnInit {
  email:string;
  loading:boolean = false;

  constructor(private authenticationService:AuthenticationService, private router:Router, private toastr: ToastsManager, public vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
  }

  send() {
    this.loading = true;
    this.authenticationService.sendActivationEmail(this.email).then(() => {
      this.toastr.success("Un email vous a été envoyé pour activer votre compte.");
      this.loading = false;
      setTimeout(() => this.router.navigate(['/unauth/home']), 2000);
    })
      .catch(error => {
        //console.debug("catch", error);
        this.loading = false;
        this.toastr.error(error.json().message);
      });
  }
}
