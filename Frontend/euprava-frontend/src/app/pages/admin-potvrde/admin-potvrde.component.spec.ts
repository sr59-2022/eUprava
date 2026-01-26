import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPotvrdeComponent } from './admin-potvrde.component';

describe('AdminPotvrdeComponent', () => {
  let component: AdminPotvrdeComponent;
  let fixture: ComponentFixture<AdminPotvrdeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPotvrdeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPotvrdeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
