import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { GrupoFamiliarService } from '../services/grupo-familiar.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-grupo-familiar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './grupo-familiar.component.html',
  styleUrls: ['./grupo-familiar.component.css']
})
export class GrupoFamiliarComponent {

  nombreGrupo = '';
  mensaje = '';

  // solo para demo visual (sin listar backend todavía)
  idGrupoEliminar?: number;

  constructor(
    private grupoService: GrupoFamiliarService,
    private authService: AuthService
  ) {}

  crearGrupo(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) {
      this.mensaje = 'Sesión no válida';
      return;
    }

    this.grupoService
      .crearGrupo(this.nombreGrupo, usuario.idUsuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Grupo familiar creado';
          this.nombreGrupo = '';
        },
        error: () => {
          this.mensaje = 'No se pudo crear el grupo';
        }
      });
  }

  eliminarGrupo(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario || !this.idGrupoEliminar) return;

    this.grupoService
      .eliminarGrupo(this.idGrupoEliminar, usuario.idUsuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Grupo eliminado';
          this.idGrupoEliminar = undefined;
        },
        error: () => {
          this.mensaje = 'No se pudo eliminar el grupo';
        }
      });
  }
}
