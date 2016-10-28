/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { VisitService } from './visit.service';

describe('Service: Visit', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VisitService]
    });
  });

  it('should ...', inject([VisitService], (service: VisitService) => {
    expect(service).toBeTruthy();
  }));
});
