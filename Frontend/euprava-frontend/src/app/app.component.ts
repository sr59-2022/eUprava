import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import {CommonModule} from '@angular/common';
import {AuthService} from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router, public authService: AuthService) {}
  title = 'euprava-frontend';

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  isAdmin(): boolean {
    const role = localStorage.getItem('role');
    return role === 'ROLE_ADMIN';
  }

  goToHome() {
    this.router.navigate(['/home-sluzba']);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }


}
