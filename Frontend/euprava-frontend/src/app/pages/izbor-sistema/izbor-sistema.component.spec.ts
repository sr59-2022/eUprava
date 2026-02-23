import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzborSistemaComponent } from './izbor-sistema.component';

describe('IzborSistemaComponent', () => {
  let component: IzborSistemaComponent;
  let fixture: ComponentFixture<IzborSistemaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IzborSistemaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IzborSistemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
