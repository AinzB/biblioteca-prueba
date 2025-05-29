import { TestBed } from '@angular/core/testing';

import { CopialibroService } from './copialibro.service';

describe('CopialibroService', () => {
  let service: CopialibroService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CopialibroService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
