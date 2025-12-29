import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfig } from '../config/api.config';
import {
  LoginRequest,
  RegistroRequest,
  UsuarioResponseDTO
} from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  // Login
  login(datos: LoginRequest) {
    return this.http.post<UsuarioResponseDTO>(
      ApiConfig.USUARIOS.LOGIN,
      datos
    );
  }

  // Registro
  registrar(datos: RegistroRequest) {
    return this.http.post(
      ApiConfig.USUARIOS.REGISTRO,
      datos,
      { responseType: 'text' }
    );
  }
  

  // Manejo de sesión simple
  guardarSesion(usuario: UsuarioResponseDTO) {
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  obtenerSesion(): UsuarioResponseDTO | null {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
  }

  logout(): void {
    localStorage.removeItem('usuario');
  }  

  estaAutenticado(): boolean {
    return localStorage.getItem('usuario') !== null;
  }
}
