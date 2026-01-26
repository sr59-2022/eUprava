import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import {CommonModule} from '@angular/common';
import {AuthService} from './services/auth.service';
import {AdminPotvrdeComponent} from './pages/admin-potvrde/admin-potvrde.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule, AdminPotvrdeComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  showAdminList = false;
  constructor(private router: Router, public authService: AuthService) {}
  title = 'euprava-frontend';


  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
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
