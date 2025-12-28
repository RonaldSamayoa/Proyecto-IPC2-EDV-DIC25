import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { WelcomeComponent } from './public/welcome/welcome.component';

export const routes: Routes = [
  // página de bienvenida
  { path: '', component: WelcomeComponent },

  // autenticación
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'registro-empresa', component: RegistroEmpresaComponent },

  // wildcard por seguridad
  { path: '**', redirectTo: '' }
];
