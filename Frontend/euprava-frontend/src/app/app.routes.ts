import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'registracija',
    loadComponent: () => import('./pages/registracija/registracija.component')
      .then(m => m.RegistracijaComponent)
  },
  { path: '', redirectTo: '/registracija', pathMatch: 'full' }

];
