import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';
import { UsuarioGrupo } from '../models/usuario-grupo.model';

@Injectable({
  providedIn: 'root'
})
export class GrupoUsuarioService {

  constructor(private http: HttpClient) {}

  listarUsuarios(idGrupo: number): Observable<UsuarioGrupo[]> {
    return this.http.get<UsuarioGrupo[]>(
      `${ApiConfig.GRUPOS.USUARIO}?tipo=usuarios&idGrupo=${idGrupo}`
    );
  }

  agregarUsuario(idGrupo: number, idUsuario: number): Observable<any> {
    return this.http.post(
      ApiConfig.GRUPOS.USUARIO,
      { idGrupo, idUsuario },
      { responseType: 'text' }
    );
  }

  quitarUsuario(idGrupo: number, idUsuario: number): Observable<any> {
    return this.http.delete(
      `${ApiConfig.GRUPOS.USUARIO}?idGrupo=${idGrupo}&idUsuario=${idUsuario}`,
      { responseType: 'text' }
    );
  }
}
