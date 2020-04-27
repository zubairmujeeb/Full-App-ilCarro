import { TestBed, async, inject } from '@angular/core/testing';

import { AuthBlockGuard } from './auth-block.guard';

describe('AuthBlockGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthBlockGuard]
    });
  });

  it('should ...', inject([AuthBlockGuard], (guard: AuthBlockGuard) => {
    expect(guard).toBeTruthy();
  }));
});
