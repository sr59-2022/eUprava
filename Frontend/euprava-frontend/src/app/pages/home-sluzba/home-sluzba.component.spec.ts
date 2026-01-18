import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeSluzbaComponent } from './home-sluzba.component';

describe('HomeSluzbaComponent', () => {
  let component: HomeSluzbaComponent;
  let fixture: ComponentFixture<HomeSluzbaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeSluzbaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeSluzbaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
