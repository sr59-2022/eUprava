import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { FakultetService } from '../../services/fakultet.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterOutlet],
  templateUrl: './app-layout.component.html',
})
export class AppLayoutComponent {
  private auth = inject(AuthService);
  public router = inject(Router);
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


  isDiplomirani(): boolean {
    return this.auth.getRoles().includes('ROLE_STUDENT');
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  posaljiDiplomirane() {
    this.fakultetService.posaljiDiplomirane().subscribe({
      next: (res) => alert(res),
      error: () => alert('Greška pri slanju diplomiranih studenata!')
    });
  }

  canSwitchSystem(): boolean {
    const roles = this.auth.getRoles();

    const hasFakultetRole =
      roles.includes('ROLE_PROFESOR') || roles.includes('ROLE_STUDENT');

    const hasSluzbaRole =
      roles.includes('ROLE_ADMIN') ||
      roles.includes('ROLE_POSLODAVAC') ||
      roles.includes('ROLE_GRADJANIN');

    return hasFakultetRole && hasSluzbaRole;
  }
}
