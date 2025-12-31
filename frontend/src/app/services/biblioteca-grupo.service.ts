import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class BibliotecaGrupoService {

  constructor(private http: HttpClient) {}

  listarPorGrupo(idGrupo: number, idUsuario: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${ApiConfig.BASE_URL}/biblioteca/grupo?idGrupo=${idGrupo}&idUsuario=${idUsuario}`
    );
  }
}
