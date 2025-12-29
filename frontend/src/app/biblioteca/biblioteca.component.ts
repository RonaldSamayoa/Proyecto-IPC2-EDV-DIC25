import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { RouterModule } from '@angular/router';
import { BibliotecaService } from '../services/biblioteca.service';
import { VideojuegoService } from '../services/videojuego.service';
import { AuthService } from '../services/auth.service';

import { Videojuego } from '../models/videojuego.model';
import { BibliotecaItem } from '../models/biblioteca.model';

interface BibliotecaVista {
  videojuego: Videojuego;
  esPropio: boolean;
}

@Component({
  selector: 'app-biblioteca',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './biblioteca.component.html',
  styleUrls: ['./biblioteca.component.css']
})
export class BibliotecaComponent implements OnInit {

  biblioteca: BibliotecaVista[] = [];
  cargando = true;
  error = '';

  constructor(
    private bibliotecaService: BibliotecaService,
    private videojuegoService: VideojuegoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.obtenerSesion();

    if (!usuario) {
      this.error = 'Sesión no válida';
      return;
    }

    this.bibliotecaService.obtenerBiblioteca(usuario.idUsuario).subscribe({
      next: (items: BibliotecaItem[]) => {
        if (items.length === 0) {
          this.cargando = false;
          return;
        }

        const requests = items.map(item =>
          this.videojuegoService.obtenerPorId(item.idJuego)
        );

        forkJoin(requests).subscribe({
          next: (videojuegos) => {
            this.biblioteca = videojuegos.map((v, i) => ({
              videojuego: v,
              esPropio: items[i].esPropio
            }));
            this.cargando = false;
          },
          error: () => {
            this.error = 'Error al cargar videojuegos';
            this.cargando = false;
          }
        });
      },
      error: () => {
        this.error = 'Error al cargar biblioteca';
        this.cargando = false;
      }
    });
  }
}

