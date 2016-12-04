/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DesactivateComponent } from './desactivate.component';

describe('DesactivateComponent', () => {
  let component: DesactivateComponent;
  let fixture: ComponentFixture<DesactivateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DesactivateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DesactivateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
