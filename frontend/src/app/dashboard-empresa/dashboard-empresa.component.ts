import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { ApiConfig } from '../config/api.config';
import { NavbarEmpresaComponent } from '../shared/navbar-empresa/navbar-empresa.component';

@Component({
  selector: 'app-dashboard-empresa',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NavbarEmpresaComponent
  ],
  templateUrl: './dashboard-empresa.component.html',
  styleUrls: ['./dashboard-empresa.component.css']
})
export class DashboardEmpresaComponent implements OnInit {

  // usuario logueado
  usuario: any;

  // id de la empresa (OBLIGATORIO PARA REGISTRAR JUEGOS)
  idEmpresa!: number;

  // formulario de videojuego (DTO SIMPLE, NO el modelo completo)
  videojuego = {
    titulo: '',
    descripcion: '',
    precio: 0,
    requisitosMinimos: '',
    clasificacionEdad: '',
    fechaLanzamiento: ''
  };

  error = '';

  constructor(
    private authService: AuthService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.usuario = this.authService.obtenerSesion();
    if (!this.usuario) {
      this.error = 'No hay sesión activa';
      return;
    }

    //OBTENER ID EMPRESA DEL USUARIO
    this.http
      .get<number>(
        `${ApiConfig.BASE_URL}/empresa/usuario?idUsuario=${this.usuario.idUsuario}`
      )
      .subscribe({
        next: id => this.idEmpresa = id,
        error: () => this.error = 'No se pudo obtener la empresa'
      });
  }

  registrarJuego(): void {
    this.error = '';

    if (!this.idEmpresa) {
      this.error = 'Empresa no válida';
      return;
    }

    const body = {
      ...this.videojuego,
      idEmpresa: this.idEmpresa
    };

    this.http.post(
      `${ApiConfig.BASE_URL}/videojuegos`,
      body,
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        alert('Videojuego registrado correctamente');
        this.resetFormulario();
      },
      error: () => {
        this.error = 'Error al registrar videojuego';
      }
    });
  }

  resetFormulario(): void {
    this.videojuego = {
      titulo: '',
      descripcion: '',
      precio: 0,
      requisitosMinimos: '',
      clasificacionEdad: '',
      fechaLanzamiento: ''
    };
  }
}






