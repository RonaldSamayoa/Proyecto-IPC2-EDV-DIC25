import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { VideojuegoService } from '../services/videojuego.service';
import { CompraService } from '../services/compra.service';
import { AuthService } from '../services/auth.service';
import { BibliotecaService } from '../services/biblioteca.service';

import { Videojuego } from '../models/videojuego.model';
import { BibliotecaItem } from '../models/biblioteca.model';
import { CategoriaService } from '../services/categoria.service';
import { Categoria } from '../models/categoria.model';


@Component({
  selector: 'app-tienda',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './tienda.component.html',
  styleUrls: ['./tienda.component.css']
})
export class TiendaComponent implements OnInit {

  videojuegos: Videojuego[] = [];
  idsEnBiblioteca: number[] = [];
  categorias: Categoria[] = [];
  categoriaSeleccionada = 0;
  
  videojuegosOriginales: Videojuego[] = [];
  
  mensaje = '';
  cargando = true;

  constructor(
    private videojuegoService: VideojuegoService,
    private compraService: CompraService,
    private authService: AuthService,
    private bibliotecaService: BibliotecaService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.cargarTienda();
    this.cargarBiblioteca();
    this.cargarCategorias();
  }

  private cargarTienda(): void {
    this.videojuegoService.listarActivos().subscribe({
      next: (data: Videojuego[]) => {
        this.videojuegosOriginales = data;
        this.videojuegos = data;
        this.cargando = false;
      },
      error: () => {
        this.mensaje = 'Error al cargar tienda';
        this.cargando = false;
      }
    });
  }

  private cargarBiblioteca(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.bibliotecaService
      .obtenerBiblioteca(usuario.idUsuario)
      .subscribe((items: BibliotecaItem[]) => {
        this.idsEnBiblioteca = items.map(
          (item: BibliotecaItem) => item.idJuego
        );
      });
  }

  private cargarCategorias(): void {
    this.categoriaService.listarActivas().subscribe({
      next: (data: Categoria[]) => this.categorias = data
    });
  }
  
  yaComprado(idJuego: number): boolean {
    return this.idsEnBiblioteca.includes(idJuego);
  }

  comprar(idJuego: number): void {
    const usuario = this.authService.obtenerSesion();

    if (!usuario) {
      this.mensaje = 'Sesión no válida';
      return;
    }

    this.compraService.comprar(usuario.idUsuario, idJuego).subscribe({
      next: () => {
        this.mensaje = 'Compra realizada con éxito';
        this.idsEnBiblioteca.push(idJuego); // se actualiza visualmente
      },
      error: () => {
        this.mensaje = 'No se pudo completar la compra';
      }
    });
  }
}
