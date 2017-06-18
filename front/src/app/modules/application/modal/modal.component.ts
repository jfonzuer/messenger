import {Component, EventEmitter, HostListener, Input, OnInit, Output} from "@angular/core";
import {Image} from "../../../model/image";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  @Input() selectedImage: Image;

  uploadImageUrl: string = environment.uploadImageUrl;

  @Output() imageClosed = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  @HostListener('document:keyup', ['$event'])
  onKeyUp(ev:KeyboardEvent) {
    if (ev.key === 'Escape') {
      this.close();
    }
  }

  close() {
    this.selectedImage = null
    this.imageClosed.emit();
  }
}
