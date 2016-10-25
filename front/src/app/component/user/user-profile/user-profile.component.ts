import { Component, OnInit } from '@angular/core';
import {Params, ActivatedRoute} from "@angular/router";
import {User} from "../../../model/user";
import {UserService} from "../../../service/user.service";
import {ConversationService} from "../../../service/conversation.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: 'user-profile.component.html',
  styleUrls: ['user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

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
