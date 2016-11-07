/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { UserParametersComponent } from './user-parameters.component';

describe('UserParametersComponent', () => {
  let component: UserParametersComponent;
  let fixture: ComponentFixture<UserParametersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserParametersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserParametersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
