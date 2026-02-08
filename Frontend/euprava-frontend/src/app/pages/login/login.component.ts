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

    this.authService.login(this.korisnickoIme, this.lozinka).subscribe({
      next: (res) => {

        const roles: string[] = res.uloge ?? [];

        // čuvamo token i role JEDNOM
        this.authService.saveToken(res.token, roles);

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
        if (
          roles.includes('ROLE_ADMIN') ||
          roles.includes('ROLE_POSLODAVAC') ||
          roles.includes('ROLE_GRADJANIN')
        ) {
          this.router.navigate(['/home-sluzba']);
          return;
        }

        // fallback (ako nema role — ne bi smelo)
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
