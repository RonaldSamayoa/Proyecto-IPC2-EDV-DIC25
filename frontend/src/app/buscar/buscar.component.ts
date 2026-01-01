import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService, UsuarioBusqueda } from '../services/usuario.service';
import { Router } from '@angular/router';
import { NavbarGamerComponent } from '../shared/navbar-gamer/navbar-gamer.component';

@Component({
  selector: 'app-buscar',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarGamerComponent],
  templateUrl: './buscar.component.html',
  styleUrls: ['./buscar.component.css']
})
export class BuscarComponent {

  termino = '';
  usuarios: UsuarioBusqueda[] = [];
  cargando = false;
  error = '';

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  buscar(): void {
    if (this.termino.trim().length < 2) {
      this.usuarios = [];
      return;
    }

    this.cargando = true;
    this.error = '';

    this.usuarioService.buscarUsuarios(this.termino).subscribe({
      next: res => {
        this.usuarios = res;
        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al buscar usuarios';
        this.cargando = false;
      }
    });
  }

  verPerfil(usuario: UsuarioBusqueda): void {
    this.router.navigate(['/perfil', usuario.idUsuario]);
  }
}
