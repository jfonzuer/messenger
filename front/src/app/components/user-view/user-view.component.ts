import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user";
import {ActivatedRoute, Params} from "@angular/router";
import {UserService} from "../../services/user.service";
import {ConversationService} from "../../services/conversation.service";

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {


  user:User;
  loading:boolean = true;

  constructor(private route:ActivatedRoute, private userService:UserService) { }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];// (+) converts string 'id' to a number
      this.userService.getUserById(id).then(user => { console.log(user); this.user = user; this.loading = false; });
    })
  }
}
