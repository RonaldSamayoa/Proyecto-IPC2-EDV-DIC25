import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { VideojuegoService } from '../services/videojuego.service';
import { CompraService } from '../services/compra.service';
import { AuthService } from '../services/auth.service';

import { VideojuegoDetalle } from '../models/videojuego-detalle.model';

@Component({
  selector: 'app-detalle-videojuego',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detalle-videojuego.component.html',
  styleUrls: ['./detalle-videojuego.component.css']
})
export class DetalleVideojuegoComponent implements OnInit {

  videojuego!: VideojuegoDetalle;
  mensaje = '';
  cargando = true;

  constructor(
    private route: ActivatedRoute,
    private videojuegoService: VideojuegoService,
    private compraService: CompraService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
  
    this.videojuegoService.obtenerDetalle(id).subscribe({
      next: (data) => {
        this.videojuego = data;
        this.cargando = false; 
      },
      error: () => {
        this.mensaje = 'Error al cargar el videojuego';
        this.cargando = false; 
      }
    });
  }

  comprar(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.compraService.comprar(usuario.idUsuario, this.videojuego.idJuego)
      .subscribe({
        next: () => this.mensaje = 'Compra realizada',
        error: () => this.mensaje = 'No se pudo completar la compra'
      });
  }
}
