import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";
import {ActivatedRoute, Params} from "@angular/router";
import {ConversationService} from "../../service/conversation.service";
import * as moment from 'moment/moment';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})

export class ProfileComponent implements OnInit {

  user:User;
  loading:boolean = true;

  constructor(private route:ActivatedRoute, private userService:UserService, private conversationService:ConversationService) { }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];// (+) converts string 'id' to a number
      this.userService.getUserById(id).then(user => { console.log(user); this.user = user; this.loading = false; });
    })
  }
}
