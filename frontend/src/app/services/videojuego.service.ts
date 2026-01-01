import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';
import { Videojuego } from '../models/videojuego.model';
import { VideojuegoDetalle } from '../models/videojuego-detalle.model';
import { VideojuegoCreate } from '../models/videojuego-create.model';


@Injectable({
  providedIn: 'root'
})
export class VideojuegoService {

  constructor(private http: HttpClient) {}

  listarActivos(): Observable<Videojuego[]> {
    return this.http.get<Videojuego[]>(ApiConfig.VIDEOJUEGOS.LISTAR);
  }

  obtenerPorId(idJuego: number): Observable<Videojuego> {
    return this.http.get<Videojuego>(
      `${ApiConfig.VIDEOJUEGOS.LISTAR}?id=${idJuego}`
    );
  }

  obtenerDetalle(idJuego: number) {
    return this.http.get<VideojuegoDetalle>(
      `${ApiConfig.VIDEOJUEGOS.DETALLE}?id=${idJuego}`
    );
  }

  // REGISTRAR VIDEOJUEGO (EMPRESA)
  registrar(videojuego: VideojuegoCreate) {
    return this.http.post(
      ApiConfig.VIDEOJUEGOS.LISTAR,
      videojuego,
      { responseType: 'text' }
    );
  }
  

// LISTAR JUEGOS DE UNA EMPRESA
listarPorEmpresa(idEmpresa: number): Observable<Videojuego[]> {
  return this.http.get<Videojuego[]>(
    `${ApiConfig.VIDEOJUEGOS.LISTAR}?idEmpresa=${idEmpresa}`
  );
}

// CAMBIAR ESTADO (activar / desactivar)
cambiarEstado(idJuego: number, activo: boolean): Observable<any> {
  return this.http.delete(
    `${ApiConfig.VIDEOJUEGOS.LISTAR}?id=${idJuego}&activo=${activo}`,
    { responseType: 'text' }
  );
}


}
