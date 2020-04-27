import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MostPopularCarsComponent } from './most-popular-cars.component';

describe('MostPopularCarsComponent', () => {
  let component: MostPopularCarsComponent;
  let fixture: ComponentFixture<MostPopularCarsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MostPopularCarsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MostPopularCarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
