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

        this.authService.saveToken(res.token, res.uloge);


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
