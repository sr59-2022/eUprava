import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  standalone: true,
  template: ''
})
export class FakultetHomeComponent implements OnInit {
  private auth = inject(AuthService);
  private router = inject(Router);

  ngOnInit(): void {
    const roles = this.auth.getRoles();

    if (roles.includes('ROLE_PROFESOR') || roles.includes('ROLE_ADMIN')) {
      this.router.navigate(['/app/profesor/ocene']);
      return;
    }

    if (roles.includes('ROLE_STUDENT')) {
      this.router.navigate(['/app/fakultet']);
      return;
    }

    this.router.navigate(['/home-sluzba']);
  }
}
