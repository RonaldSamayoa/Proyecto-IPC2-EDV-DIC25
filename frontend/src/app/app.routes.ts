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


export const routes: Routes = [
  // página de bienvenida
  { path: '', component: WelcomeComponent },

  // autenticación
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'registro-empresa', component: RegistroEmpresaComponent },
  
  { path: 'dashboard-gamer', component: DashboardGamerComponent },
  { path: 'dashboard-empresa', component: DashboardEmpresaComponent },

  { path: 'cartera', component: CarteraComponent },

  { path: 'biblioteca', component: BibliotecaComponent },

  { path: 'tienda', component: TiendaComponent },

  { path: 'videojuego/:id', component: DetalleVideojuegoComponent },
  
  { path: 'familia', component: GrupoFamiliarComponent },

  {path:'grupo/:idGrupo/usuarios', component: GrupoUsuarioComponent},

  

  // wildcard por seguridad
  { path: '**', redirectTo: '' }

];
