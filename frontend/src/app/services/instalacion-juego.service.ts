import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class InstalacionJuegoService {

  constructor(private http: HttpClient) {}

  instalarJuego(
    idUsuario: number,
    idJuego: number,
    esPrestado: boolean,
    idDueno?: number
  ): Observable<any> {

    const body: any = {
      idUsuario,
      idJuego,
      esPrestado
    };

    if (esPrestado && idDueno) {
      body.idDueno = idDueno;
    }

    return this.http.post(
      `${ApiConfig.BASE_URL}/instalacion`,
      body,
      { responseType: 'text' }
    );
  }

  desinstalarJuego(
    idUsuario: number, idJuego: number): Observable<any> {
    return this.http.delete(
      `${ApiConfig.BASE_URL}/instalacion?idUsuario=${idUsuario}&idJuego=${idJuego}`,
      { responseType: 'text' }
    );
  }

  estaInstalado(idUsuario: number, idJuego: number) {
    return this.http.get<{ instalado: boolean }>(
      `${ApiConfig.BASE_URL}/instalacion/estado`,
      {
        params: {
          idUsuario,
          idJuego
        }
      }
    );
  }
  

}
