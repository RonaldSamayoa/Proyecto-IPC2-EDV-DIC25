import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { GrupoFamiliarService } from '../services/grupo-familiar.service';
import { AuthService } from '../services/auth.service';
import { GrupoFamiliar } from '../models/grupo-familiar.model';

@Component({
  selector: 'app-grupo-familiar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './grupo-familiar.component.html',
  styleUrls: ['./grupo-familiar.component.css']
})
export class GrupoFamiliarComponent implements OnInit {

  nombreGrupo = '';
  mensaje = '';
  grupos: GrupoFamiliar[] = [];

  constructor(
    private grupoService: GrupoFamiliarService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarGrupos();
  }

  cargarGrupos(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.grupoService
      .listarPorUsuario(usuario.idUsuario)
      .subscribe(grupos => this.grupos = grupos);
  }

  crearGrupo(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.grupoService
      .crearGrupo(this.nombreGrupo, usuario.idUsuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Grupo creado';
          this.nombreGrupo = '';
          this.cargarGrupos();
        },
        error: () => this.mensaje = 'No se pudo crear el grupo'
      });
  }

  eliminarGrupo(idGrupo: number): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.grupoService
      .eliminarGrupo(idGrupo, usuario.idUsuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Grupo eliminado';
          this.cargarGrupos();
        },
        error: () => this.mensaje = 'No se pudo eliminar el grupo'
      });
  }

  esCreador(grupo: GrupoFamiliar): boolean {
    const usuario = this.authService.obtenerSesion();
    return usuario?.idUsuario === grupo.idCreador;
  }

  entrarGrupo(idGrupo: number): void {
    this.router.navigate(['/grupo', idGrupo, 'usuarios']);
  }
}

