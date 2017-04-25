/**
 * Created by pgmatz on 31/10/16.
 */

import {Directive, Renderer, ElementRef, Input, HostListener, OnInit} from "@angular/core";
import * as moment from 'moment/moment';

@Directive({ selector: '[myFormatDate]'})
export class FormatDateDirective implements OnInit {

  @Input('myFormatDate') myDate: string;

  constructor( private el:ElementRef, private renderer: Renderer) {
  }

  ngOnInit(): void {
    //moment(visit.visitedDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
  }
}
