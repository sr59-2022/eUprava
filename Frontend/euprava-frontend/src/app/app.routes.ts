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

  { path: '', redirectTo: '/login', pathMatch: 'full' },

  {
    path: 'home-sluzba',
    loadComponent: () => import('./pages/home-sluzba/home-sluzba.component')
      .then(m => m.HomeSluzbaComponent)
  },

  {
    path: 'profil',
    loadComponent: () => import('./pages/profil/profil.component')
        .then(m => m.ProfilComponent)
  },

  {
    path: 'profil/uredi',
    loadComponent: () => import('./pages/profil-edit/profil-edit.component')
        .then(m => m.ProfilEditComponent)
  },

  {
    path: 'admin/potvrde',
    loadComponent: () => import('./pages/admin-potvrde/admin-potvrde.component')
      .then(m => m.AdminPotvrdeComponent)
  },
  {
    path: 'prijave/poslodavac',
    loadComponent: () => import('./pages/prijave-poslodavac/prijave-poslodavac.component')
      .then(m => m.PrijavePoslodavacComponent)
  },

  {
    path: 'obavestenja',
    loadComponent: () => import('./pages/obavestenja/obavestenja.component')
      .then(m => m.ObavestenjaComponent)
  },


  {
    path: 'app',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./layouts/app-layout/app-layout.component').then(m => m.AppLayoutComponent),
    children: [
      {
        path: '',
        pathMatch: 'full',
        loadComponent: () =>
          import('./pages/fakultet-home/fakultet-home.component')
            .then(m => m.FakultetHomeComponent),
      },

      {
        path: 'profesor/ocene',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_PROFESOR', 'ROLE_ADMIN'] },
        loadComponent: () =>
          import('./pages/profesor-ocene/profesor-ocene.component')
            .then(m => m.ProfesorOceneComponent),
      },

      {
        path: 'profesor/ispiti',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_PROFESOR', 'ROLE_ADMIN'] },
        loadComponent: () =>
          import('./pages/profesor-ispiti/profesor-ispiti.component')
            .then(m => m.ProfesorIspitiComponent),
      },



      {
        path: 'prijava-ispita',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_STUDENT'] },
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

      {
        path: 'profesor/studenti',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_PROFESOR', 'ROLE_ADMIN'] },
        loadComponent: () =>
          import('./pages/profesor-studenti/profesor-studenti.component')
            .then(m => m.ProfesorStudentiComponent),
      },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];
