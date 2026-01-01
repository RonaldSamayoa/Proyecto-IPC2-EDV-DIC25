import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';

export interface UsuarioBusqueda {
  idUsuario: number;
  nickname: string;
  correo: string;
  pais: string;
  rol: string;
  bibliotecaPublica: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) {}

  buscarUsuarios(texto: string): Observable<UsuarioBusqueda[]> {
    return this.http.get<UsuarioBusqueda[]>(
      `${ApiConfig.BASE_URL}/usuarios/buscar`,
      { params: { q: texto } }
    );
  }
}
