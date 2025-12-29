import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';
import { BibliotecaItem } from '../models/biblioteca.model';

@Injectable({
  providedIn: 'root'
})
export class BibliotecaService {

  constructor(private http: HttpClient) {}

  obtenerBiblioteca(idUsuario: number): Observable<BibliotecaItem[]> {
    return this.http.get<BibliotecaItem[]>(
      `${ApiConfig.BIBLIOTECA.LISTAR}?idUsuario=${idUsuario}`
    );
  }
}
