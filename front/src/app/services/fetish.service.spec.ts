/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FetishService } from './fetish.service';

describe('Service: Fetish', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FetishService]
    });
  });

  it('should ...', inject([FetishService], (service: FetishService) => {
    expect(service).toBeTruthy();
  }));
});
