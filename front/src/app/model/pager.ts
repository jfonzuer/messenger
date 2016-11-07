import {Response} from "@angular/http";
/**
 * Created by pgmatz on 07/11/16.
 */

export class Pager {
  constructor(public page:number, public last:boolean, public size:number) {}
}
