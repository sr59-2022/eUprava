import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajOglasComponent } from './dodaj-oglas.component';

describe('DodajOglasComponent', () => {
  let component: DodajOglasComponent;
  let fixture: ComponentFixture<DodajOglasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DodajOglasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DodajOglasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
