import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LastFeedsComponent } from './last-feeds.component';

describe('LastFeedsComponent', () => {
  let component: LastFeedsComponent;
  let fixture: ComponentFixture<LastFeedsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LastFeedsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LastFeedsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
