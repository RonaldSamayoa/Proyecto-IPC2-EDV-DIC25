import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class CompraService {

  constructor(private http: HttpClient) {}

  comprar(idUsuario: number, idJuego: number): Observable<any> {
    return this.http.post(ApiConfig.COMPRAS.COMPRAR, {
      idUsuario,
      idJuego
    });
  }

  
}
