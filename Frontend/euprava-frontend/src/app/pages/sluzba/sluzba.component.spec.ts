import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SluzbaComponent } from './sluzba.component';

describe('SluzbaComponent', () => {
  let component: SluzbaComponent;
  let fixture: ComponentFixture<SluzbaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SluzbaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SluzbaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
