// src/app/pages/login/login.component.ts
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

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.korisnickoIme, this.lozinka).subscribe({
      next: (res) => {
        this.authService.saveToken(res.token, Array.from(res.uloge));
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Neuspešna prijava. Proveri korisničko ime i lozinku.';
      }
    });
  }
  navigateToRegister() {
    this.router.navigate(['registracija']);
  }
}

