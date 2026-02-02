import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { roleGuard } from './guards/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then(m => m.LoginComponent),
  },
  {
    path: 'registracija',
    loadComponent: () =>
      import('./pages/registracija/registracija.component').then(m => m.RegistracijaComponent),
  },
  {
    path: 'app',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./layouts/app-layout/app-layout.component').then(m => m.AppLayoutComponent),
    children: [
      { path: '', redirectTo: 'prijava-ispita', pathMatch: 'full' },

      {
        path: 'prijava-ispita',
        loadComponent: () =>
          import('./pages/prijava-ispita/prijava-ispita.component')
            .then(m => m.PrijavaIspitaComponent),
      },
      {
        path: 'fakultet',
        loadComponent: () =>
          import('./pages/fakultet/fakultet.component')
            .then(m => m.FakultetComponent),
      },
      {
        path: 'diplomiranje',
        loadComponent: () =>
          import('./pages/diplomiranje/diplomiranje.component')
            .then(m => m.DiplomiranjeComponent),
      },
      {
        path: 'uverenja',
        loadComponent: () =>
          import('./pages/uverenja/uverenja.component')
            .then(m => m.UverenjaComponent),
      },
      {
        path: 'sluzba',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN', 'ROLE_SLUZBA'] },
        loadComponent: () =>
          import('./pages/sluzba/sluzba.component')
            .then(m => m.SluzbaComponent),
      },
    ],

  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];
