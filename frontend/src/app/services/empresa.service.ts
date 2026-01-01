import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiConfig } from '../config/api.config';

@Injectable({ providedIn: 'root' })
export class EmpresaService {

  constructor(private http: HttpClient) {}

  obtenerEmpresaPorUsuario(idUsuario: number) {
    return this.http.get<number>(
      `${ApiConfig.BASE_URL}/empresa/usuario?idUsuario=${idUsuario}`
    );
  }
}
