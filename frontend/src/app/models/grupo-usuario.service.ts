import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfig } from '../config/api.config';

@Injectable({
    providedIn: 'root'
  })
  export class GrupoUsuarioService {
  
    constructor(private http: HttpClient) {}
  
    listarGruposDeUsuario(idUsuario: number) {
      return this.http.get<number[]>(
        `${ApiConfig.BASE_URL}/grupo-usuario?tipo=grupos&idUsuario=${idUsuario}`
      );
    }
  
    listarUsuariosDeGrupo(idGrupo: number) {
      return this.http.get<number[]>(
        `${ApiConfig.BASE_URL}/grupo-usuario?tipo=usuarios&idGrupo=${idGrupo}`
      );
    }
  
    agregarUsuario(idGrupo: number, idUsuario: number) {
      return this.http.post(
        `${ApiConfig.BASE_URL}/grupo-usuario`,
        { idGrupo, idUsuario }
      );
    }
  
    eliminarUsuario(idGrupo: number, idUsuario: number) {
      return this.http.delete(
        `${ApiConfig.BASE_URL}/grupo-usuario?idGrupo=${idGrupo}&idUsuario=${idUsuario}`
      );
    }
  }
  
