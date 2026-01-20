import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'registracija',
    loadComponent: () => import('./pages/registracija/registracija.component')
      .then(m => m.RegistracijaComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component')
      .then(m => m.LoginComponent)
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
  }


];
