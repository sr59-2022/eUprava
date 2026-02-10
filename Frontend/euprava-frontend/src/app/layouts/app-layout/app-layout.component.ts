import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import {FakultetService} from '../../services/fakultet.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterOutlet],
  templateUrl: './app-layout.component.html',
})
export class AppLayoutComponent {
  private auth = inject(AuthService);
  private router = inject(Router);
  private fakultetService = inject(FakultetService);

  get isAdmin(): boolean {
    return this.auth.isAdmin();
  }

  get isProfesor(): boolean {
    return this.auth.getRoles().includes('ROLE_PROFESOR');
  }

  get isStudent(): boolean {
    return this.auth.getRoles().includes('ROLE_STUDENT');
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  posaljiDiplomirane() {
    this.fakultetService.posaljiDiplomirane()
      .subscribe({
        next: res => alert(res),
        error: err => alert('Greška pri slanju diplomiranih studenata!')
      });
  }
}
