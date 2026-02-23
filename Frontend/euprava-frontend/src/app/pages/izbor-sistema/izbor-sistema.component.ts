import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-izbor-sistema',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './izbor-sistema.component.html',
    styleUrl: './izbor-sistema.component.css'
})
export class IzborSistemaComponent {

  roles: string[] = [];

  constructor(private router: Router, private authService: AuthService) {
    this.roles = this.authService.getRoles();
  }

  goFakultet() {
    if (this.roles.includes('ROLE_PROFESOR')) {
      this.router.navigate(['/app/profesor/ocene']);
      return;
    }
    this.router.navigate(['/app']);
  }

  goSluzba() {
    this.router.navigate(['/home-sluzba']);
  }
}
