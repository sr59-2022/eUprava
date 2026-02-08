import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import {CommonModule} from '@angular/common';
import {AuthService} from './services/auth.service';
import {ObavestenjeService} from './services/obavestenje.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  showAdminList = false;
  constructor(private router: Router, public authService: AuthService, private obavestenjeService: ObavestenjeService) {}
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

  isSluzbaUser(): boolean {
    return this.authService.isGradjanin()
      || this.authService.isPoslodavac()
      || this.authService.isAdmin();
  }


}
