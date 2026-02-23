import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  korisnickoIme: string = '';
  lozinka: string = '';
  errorMsg: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login(): void {
    this.errorMsg = '';

    const body = {
      korisnickoIme: (this.korisnickoIme ?? '').trim(),
      lozinka: (this.lozinka ?? '').trim()
    };


    if (!body.korisnickoIme || !body.lozinka) {
      this.errorMsg = 'Unesi korisničko ime i lozinku.';
      return;
    }


    this.authService.login(body.korisnickoIme, body.lozinka).subscribe({
      next: (res) => {
        const roles: string[] = res.uloge ?? [];

        this.authService.saveToken(res.token, roles);

        const hasFakultetRole =
          roles.includes('ROLE_PROFESOR') || roles.includes('ROLE_STUDENT');

        const hasSluzbaRole =
          roles.includes('ROLE_ADMIN') ||
          roles.includes('ROLE_POSLODAVAC') ||
          roles.includes('ROLE_GRADJANIN');

        if (hasFakultetRole && hasSluzbaRole) {
          this.router.navigate(['/izbor-sistema']);
          return;
        }

        // FAKULTET
        if (roles.includes('ROLE_PROFESOR')) {
          this.router.navigate(['/app/profesor/ocene']);
          return;
        }

        if (roles.includes('ROLE_STUDENT')) {
          this.router.navigate(['/app']);
          return;
        }

        // SLUŽBA
        if (hasSluzbaRole) {
          this.router.navigate(['/home-sluzba']);
          return;
        }

        this.router.navigate(['/login']);
      },
      error: () => {
        this.errorMsg = 'Neuspešna prijava. Proveri korisničko ime i lozinku.';
      }
    });
  }

  navigateToRegister(): void {
    this.router.navigate(['/registracija']);
  }
}
