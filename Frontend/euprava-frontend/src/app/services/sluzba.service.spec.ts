import { TestBed } from '@angular/core/testing';

import { SluzbaService } from './sluzba.service';

describe('SluzbaService', () => {
  let service: SluzbaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SluzbaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
