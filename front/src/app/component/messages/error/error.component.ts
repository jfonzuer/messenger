import { Component, OnInit } from '@angular/core';
import {Input} from "@angular/core/src/metadata/directives";

@Component({
  selector: 'app-error',
  templateUrl: 'error.component.html',
  styleUrls: ['error.component.css']
})
export class ErrorComponent implements OnInit {

  @Input() error:string;

  constructor() { }

  ngOnInit() {
  }

}
