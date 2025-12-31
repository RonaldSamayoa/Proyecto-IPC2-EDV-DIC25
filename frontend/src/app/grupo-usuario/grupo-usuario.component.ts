import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { GrupoUsuarioService } from '../services/grupo-usuario.service';
import { UsuarioGrupo } from '../models/usuario-grupo.model';

@Component({
  selector: 'app-grupo-usuario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './grupo-usuario.component.html',
  styleUrls: ['./grupo-usuario.component.css']
})
export class GrupoUsuarioComponent implements OnInit {

  idGrupo!: number;
  idUsuario?: number;

  usuarios: UsuarioGrupo[] = [];
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private grupoUsuarioService: GrupoUsuarioService
  ) {}

  ngOnInit(): void {
    this.idGrupo = Number(this.route.snapshot.paramMap.get('idGrupo'));
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.grupoUsuarioService
      .listarUsuarios(this.idGrupo)
      .subscribe({
        next: data => this.usuarios = data,
        error: () => this.mensaje = 'Error al listar usuarios'
      });
  }  

  agregar(): void {
    if (!this.idUsuario) return;

    this.grupoUsuarioService
      .agregarUsuario(this.idGrupo, this.idUsuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Usuario agregado al grupo';
          this.idUsuario = undefined;
          this.cargarUsuarios();
        },
        error: () => this.mensaje = 'No se pudo agregar el usuario, verificar id o el limite de 5 integrantes'
      });
  }

  quitar(idUsuario: number): void {
    this.grupoUsuarioService
      .quitarUsuario(this.idGrupo, idUsuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Usuario eliminado del grupo';
          this.cargarUsuarios();
        },
        error: () => this.mensaje = 'No se pudo eliminar el usuario'
      });
  }
}

