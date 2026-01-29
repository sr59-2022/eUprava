import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrijavePoslodavacComponent } from './prijave-poslodavac.component';

describe('PrijavePoslodavacComponent', () => {
  let component: PrijavePoslodavacComponent;
  let fixture: ComponentFixture<PrijavePoslodavacComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrijavePoslodavacComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrijavePoslodavacComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
