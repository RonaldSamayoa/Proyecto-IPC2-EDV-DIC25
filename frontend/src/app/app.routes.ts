import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { WelcomeComponent } from './public/welcome/welcome.component';
import { RegistroEmpresaComponent } from './registro-empresa/registro-empresa.component';
import { DashboardGamerComponent } from './dashboard-gamer/dashboard-gamer.component';
import { DashboardEmpresaComponent } from './dashboard-empresa/dashboard-empresa.component';
import { CarteraComponent } from './cartera/cartera.component';
import { BibliotecaComponent } from './biblioteca/biblioteca.component';
import { TiendaComponent } from './tienda/tienda.component';
import { DetalleVideojuegoComponent } from './detalle-videojuego/detalle-videojuego.component';
import { GrupoFamiliarComponent } from './grupo-familiar/grupo-familiar.component';
import { GrupoUsuarioComponent } from './grupo-usuario/grupo-usuario.component';
import { GrupoBibliotecaComponent } from './grupo-biblioteca/grupo-biblioteca.component';
import { AuthGuard } from './guards/auth.guard';
import { GamerGuard } from './guards/gamer.guard';
import { EmpresaGuard } from './guards/empresa.guard';

export const routes: Routes = [
  // página de bienvenida
  { path: '', component: WelcomeComponent },

  // autenticación
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'registro-empresa', component: RegistroEmpresaComponent },
  
  //dashboards segun el rol
  { path: 'dashboard-gamer', component: DashboardGamerComponent, canActivate: [AuthGuard, GamerGuard] },
  { path: 'dashboard-empresa', component: DashboardEmpresaComponent, canActivate: [AuthGuard, EmpresaGuard] },

  { path: 'cartera', component: CarteraComponent, canActivate: [AuthGuard, GamerGuard]},

  { path: 'biblioteca', component: BibliotecaComponent, canActivate: [AuthGuard, GamerGuard] },

  { path: 'tienda', component: TiendaComponent, canActivate: [AuthGuard] },

  { path: 'videojuego/:id', component: DetalleVideojuegoComponent, canActivate: [AuthGuard] },
  
  { path: 'familia', component: GrupoFamiliarComponent, canActivate: [AuthGuard, GamerGuard] },

  { path:'grupo/:idGrupo/usuarios', component: GrupoUsuarioComponent, canActivate: [AuthGuard, GamerGuard]},

  { path: 'grupo/:idGrupo/biblioteca', component: GrupoBibliotecaComponent, canActivate: [AuthGuard, GamerGuard]},
  
  {path: 'buscar',loadComponent: () => import('./buscar/buscar.component').then(m => m.BuscarComponent)},
  

  // wildcard por seguridad
  { path: '**', redirectTo: '' }

];
