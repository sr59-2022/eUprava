import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const allowedRoles = route.data?.['roles'] as string[];
  const userRoles = auth.getRoles();

  if (!auth.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }

  if (userRoles.some(r => allowedRoles.includes(r))) {
    return true;
  }

  router.navigate(['/app']);
  return false;
};
