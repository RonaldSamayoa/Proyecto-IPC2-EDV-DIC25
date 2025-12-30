import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfig } from '../config/api.config';
import { Observable } from 'rxjs';
import { GrupoFamiliar } from '../models/grupo-familiar.model';

@Injectable({
  providedIn: 'root'
})
export class GrupoFamiliarService {

  constructor(private http: HttpClient) {}

  crearGrupo(nombre: string, idCreador: number) {
    return this.http.post(
      `${ApiConfig.BASE_URL}/grupo-familiar`,
      { nombre, idCreador },
      { responseType: 'text' }
    );
  }

  listarPorUsuario(idUsuario: number): Observable<GrupoFamiliar[]> {
    return this.http.get<GrupoFamiliar[]>(
      `${ApiConfig.BASE_URL}/grupo-familiar?idUsuario=${idUsuario}`
    );
  }
  

  eliminarGrupo(idGrupo: number, idUsuario: number) {
    return this.http.delete(
      `${ApiConfig.BASE_URL}/grupo-familiar?idGrupo=${idGrupo}&idUsuario=${idUsuario}`,
      { responseType: 'text' }  
    );
  }
}
