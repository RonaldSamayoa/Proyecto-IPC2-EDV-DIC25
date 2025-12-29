import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { VideojuegoService } from '../services/videojuego.service';
import { CompraService } from '../services/compra.service';
import { AuthService } from '../services/auth.service';

import { Videojuego } from '../models/videojuego.model';

@Component({
  selector: 'app-tienda',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './tienda.component.html',
  styleUrls: ['./tienda.component.css']
})
export class TiendaComponent implements OnInit {

  videojuegos: Videojuego[] = [];
  mensaje = '';
  cargando = true;

  constructor(
    private videojuegoService: VideojuegoService,
    private compraService: CompraService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.videojuegoService.listarActivos().subscribe({
      next: data => {
        this.videojuegos = data;
        this.cargando = false;
      },
      error: () => {
        this.mensaje = 'Error al cargar tienda';
        this.cargando = false;
      }
    });
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
      },
      error: (err) => {
        this.mensaje = 'No se pudo completar la compra';
        console.error(err);
      }
    });
  }
}
