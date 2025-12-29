import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { WelcomeComponent } from './public/welcome/welcome.component';
import { RegistroEmpresaComponent } from './registro-empresa/registro-empresa.component';
import { DashboardGamerComponent } from './dashboard-gamer/dashboard-gamer.component';
import { DashboardEmpresaComponent } from './dashboard-empresa/dashboard-empresa.component';

export const routes: Routes = [
  // página de bienvenida
  { path: '', component: WelcomeComponent },

  // autenticación
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'registro-empresa', component: RegistroEmpresaComponent },
  
  { path: 'dashboard-gamer', component: DashboardGamerComponent },
  { path: 'dashboard-empresa', component: DashboardEmpresaComponent },

  // wildcard por seguridad
  { path: '**', redirectTo: '' }

];
