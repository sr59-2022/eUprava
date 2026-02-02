import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!auth.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }

  const allowedRoles = (route.data?.['roles'] as string[]) ?? [];


  if (allowedRoles.length === 0) return true;

  const userRoles = auth.getRoles();

  if (userRoles.some(r => allowedRoles.includes(r))) {
    return true;
  }

  router.navigate(['/app']);
  return false;
};
