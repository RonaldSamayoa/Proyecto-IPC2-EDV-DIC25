import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfig } from '../config/api.config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GrupoFamiliarService {

  constructor(private http: HttpClient) {}

  crearGrupo(nombre: string, idCreador: number): Observable<any> {
    return this.http.post(
      `${ApiConfig.BASE_URL}/grupo-familiar`,
      { nombre, idCreador }
    );
  }

  eliminarGrupo(idGrupo: number, idUsuario: number): Observable<any> {
    return this.http.delete(
      `${ApiConfig.BASE_URL}/grupo-familiar?idGrupo=${idGrupo}&idUsuario=${idUsuario}`
    );
  }
}
