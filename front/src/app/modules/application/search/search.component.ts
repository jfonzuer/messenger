import {Component, OnInit, ViewContainerRef} from "@angular/core";
import {User} from "../../../model/user";
import {SharedService} from "../../../services/shared.service";
import {UserService} from "../../../services/user.service";
import {environment} from "../../../../environments/environment";
import {Pager} from "../../../model/pager";
import {ActivatedRoute} from "@angular/router";
import {UserType} from "../../../model/userType";
import {Search} from "../../../model/search";
import {DatetimeService} from "../../../services/datetime.service";
import {ToastsManager} from "ng2-toastr";
import {Constant} from "../../../model/response/constants";
import {Country} from "../../../model/country";
import {Area} from "../../../model/area";

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
  uploadImageUrl:string;
  pager:Pager;
  loading:boolean;

  ageOne:number = 18;
  ageTwo:number = 99;

  constants:Constant;
  area:Area = new Area(0);
  country:Country = new Country(0);

  constructor(private userService: UserService, private sharedService: SharedService, private route:ActivatedRoute, private datetimeService:DatetimeService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);

    this.search.heightOne = 0;
    this.search.heightTwo = 220;
    this.search.weightOne = 0;
    this.search.weightTwo = 120;
  }

  ngOnInit() {
    // init search userType
    this.search.userType = new UserType(this.sharedService.isDomina() ? 2 : 1);
    this.search.keyword = '';

    this.search.country = new Country(1);
    this.search.area = new Area(1);

    this.route.data.forEach((data:any) => {
      // si data n'est pas un array, il contient alors une erreur
      data.constants instanceof Object ? this.constants = data.constants : this.toastr.error("Erreur de connexion");

    });
    this.getUsers();
  }

  getUsers() {
    this.userService.getUsers(this.pager).then(response => {
      this.pager == null ? this.users = response.content : this.users = this.users.concat(response.content);
      this.pager = new Pager(response.number, response.last, response.size, 10);
    }).catch(e => {
      this.toastr.error(e)
    });
  }

  searchUsers() {
    this.loading = true;
    this.userService.searchUsers(this.search, this.pager).then(response => {
      this.loading = false;
      let list:User[] = response.content;
      this.searched = true;
      this.search.keyword ? this.previewFromDescription(list) : null;

      this.users = this.users.concat(response.content);
      this.pager = new Pager(response.number, response.last, response.size, 10);

    }).catch(e => {
      this.loading = false;
      this.toastr.error(e);
    });
  }

  scrollDown() {
    this.pager.page = this.pager.page + 1;
    this.searched ? this.searchUsers() : this.getUsers();
  }

  send() {
    // on fait figurer ou non dans la recherche l'objet area ou country
    this.area.id != 0 ? this.search.area = new Area(this.area.id) : this.search.area = null;
    this.country.id != 0 ? this.search.country = new Country(this.country.id) : this.search.country = null;

    // on check si le filtre d'age par défaut a été changé et s'il est cohérent
    this.ageOne != 18 || this.ageTwo != 99  ?  this.ageOne < this.ageTwo ? this.datetimeService.formatSearch(this.search, this.ageOne, this.ageTwo) : null : null;

    // reset pager to null
    this.pager =  null;
    this.users = [];
    this.searched = true;
    this.searchUsers();
  }

  previewFromDescription(users:User[]) {
    for (let u of users) {
      u.description.search(this.search.keyword);
      let iKeyword = u.description.search(this.search.keyword);
      u.description = u.description.replace(this.search.keyword, '<strong>' + this.search.keyword + '</strong>');

      // on prend les 20 caractères avant et après
      let iStart = iKeyword - 40 < 0 ? 0 : iKeyword - 40;
      let iEnd = iKeyword + 40 > u.description.length ? u.description.length : iKeyword + 40;

      u.description = '...' + u.description.substring(iStart, iEnd) + '...';
    }
  }
}
