import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SluzbaDiplomiraniComponent } from './sluzba-diplomirani.component';

describe('SluzbaDiplomiraniComponent', () => {
  let component: SluzbaDiplomiraniComponent;
  let fixture: ComponentFixture<SluzbaDiplomiraniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SluzbaDiplomiraniComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SluzbaDiplomiraniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
