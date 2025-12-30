import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../config/api.config';
import { Categoria } from '../models/categoria.model';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(private http: HttpClient) {}

  listarActivas(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(
      `${ApiConfig.BASE_URL}/categorias`
    );
  }
}
