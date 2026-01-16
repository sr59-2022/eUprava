import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router) {}
  title = 'euprava-frontend';
  // za sada simulacija "ulogovanog korisnika"
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token'); // setuj token kad registracija prođe
  }

  isAdmin(): boolean {
    const role = localStorage.getItem('role');
    return role === 'ROLE_ADMIN';
  }

  goToHome() {
    // trenutno samo redirect na registraciju, dok nema home
    this.router.navigate(['/registracija']);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/registracija']);
  }
}
