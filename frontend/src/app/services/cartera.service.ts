import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfig } from '../config/api.config';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CarteraService {

  constructor(private http: HttpClient) {}

  obtenerCartera(idUsuario: number): Observable<any> {
    return this.http.get(
      `${ApiConfig.BASE_URL}/cartera?idUsuario=${idUsuario}`
    );
  }

  recargar(idUsuario: number, monto: number): Observable<any> {
    return this.http.post(
      `${ApiConfig.BASE_URL}/cartera`,
      {
        idUsuario,
        monto,
        tipo: 'RECARGA'
      }
    );
  }
}
