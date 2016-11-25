import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {SharedService} from "../../../services/shared.service";
import {UserService} from "../../../services/user.service";
import {environment} from "../../../../environments/environment";
import {Pager} from "../../../model/pager";
import {ActivatedRoute} from "@angular/router";
import {Localization} from "../../../model/localization";
import {UserType} from "../../../model/userType";
import {Search} from "../../../model/search";
import * as moment from 'moment/moment';
import {DatetimeService} from "../../../services/datetime.service";

@Component({
  selector: 'app-search',
  templateUrl: 'search.component.html',
  styleUrls: ['search.component.css']
})
export class SearchComponent implements OnInit {

  search:Search = new Search();
  searched:boolean = false;
  users:User[];
  title:string;
  uploadUrl:string;
  pager:Pager;
  localizations:Localization[];
  types:UserType[];
  error:string;
  ageOne:number = 18;
  ageTwo:number = 99;
  localization:Localization = new Localization(0);

  constructor(private userService: UserService, private sharedService: SharedService, private route:ActivatedRoute, private datetimeService:DatetimeService) {
    this.uploadUrl = environment.uploadUrl;
  }

  ngOnInit() {
    console.log("is domina : " + this.sharedService.isDomina())
    // init search userType
    this.search.userType = new UserType(this.sharedService.isDomina() ? 2 : 1);
    this.search.keyword = '';

    this.route.data.forEach((data:any) => {
      data.localizations instanceof Array ? this.localizations = data.localizations : this.error = data.localizations;
      data.types instanceof Array ? this.types = data.types : this.error = data.types;

    });
    this.getUsers();
  }

  getUsers() {
    this.userService.getUsers(this.pager).then(response => {
      this.pager == null ? this.users = response.content : this.users = this.users.concat(response.content);
      this.pager = new Pager(response.number, response.last, response.size);
      console.log(response);
      console.log(this.pager);
      console.log(this.pager.last);
    }).catch(e => this.error = e);
  }

  searchUsers() {
    this.userService.searchUsers(this.search, this.pager).then(response => {
      this.users = this.users.concat(response.content);
      this.pager = new Pager(response.number, response.last, response.size);
    }).catch(e => {
      console.error(e);
      this.error = e;
    });
  }

  scrollDown() {
    this.pager.page = this.pager.page + 1;
    this.searched ? this.searchUsers() : this.getUsers();
  }



  send() {
    this.localization.id != 0 ? this.search.localization = new Localization(this.localization.id) : this.search.localization = null;
    // on check si le filtre d'age par défaut a été changé et s'il est cohérent
    this.ageOne != 18 || this.ageTwo != 99  ?  this.ageOne < this.ageTwo ? this.datetimeService.formatSearch(this.search, this.ageOne, this.ageTwo) : null : null;
    // reset pager to null
    this.pager =  null;
    this.users = [];
    this.searched = true;

    this.searchUsers();
  }
}
