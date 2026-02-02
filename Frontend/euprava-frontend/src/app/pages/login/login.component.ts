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
        // res.uloge je niz/string[] u tvom backu
        this.authService.saveToken(res.token, res.uloge);

        const roles: string[] = res.uloge ?? [];


        if (roles.includes('ROLE_PROFESOR')) {
          this.router.navigate(['/app/profesor/ocene']);
          return;
        }

        if (roles.includes('ROLE_STUDENT')) {
          this.router.navigate(['/app/prijava-ispita']);
          return;
        }


        this.router.navigate(['/app']);
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
