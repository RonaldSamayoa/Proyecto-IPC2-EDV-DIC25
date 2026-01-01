import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { RouterModule } from '@angular/router';

import { BibliotecaService } from '../services/biblioteca.service';
import { VideojuegoService } from '../services/videojuego.service';
import { AuthService } from '../services/auth.service';
import { InstalacionJuegoService } from '../services/instalacion-juego.service';

import { Videojuego } from '../models/videojuego.model';
import { BibliotecaItem } from '../models/biblioteca.model';
import { NavbarGamerComponent } from '../shared/navbar-gamer/navbar-gamer.component';

interface BibliotecaVista {
  videojuego: Videojuego;
  esPropio: boolean;
  instalado: boolean;
}

@Component({
  selector: 'app-biblioteca',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarGamerComponent],
  templateUrl: './biblioteca.component.html',
  styleUrls: ['./biblioteca.component.css']
})
export class BibliotecaComponent implements OnInit {

  biblioteca: BibliotecaVista[] = [];
  juegosInstalados: Videojuego[] = [];

  cargando = true;
  error = '';

  constructor(
    private bibliotecaService: BibliotecaService,
    private videojuegoService: VideojuegoService,
    private instalacionService: InstalacionJuegoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) {
      this.error = 'Sesión no válida';
      return;
    }

    forkJoin({
      biblioteca: this.bibliotecaService.obtenerBiblioteca(usuario.idUsuario),
      instalaciones: this.instalacionService.listarInstaladosPorUsuario(usuario.idUsuario)
    }).subscribe({
      next: ({ biblioteca, instalaciones }) => {

        const idsInstalados = new Set(
          instalaciones.map(i => i.idJuego)
        );

        // ---- CARGAR BIBLIOTECA (COMPRADOS) ----
        if (biblioteca.length > 0) {
          const reqBiblioteca = biblioteca.map(item =>
            this.videojuegoService.obtenerPorId(item.idJuego)
          );

          forkJoin(reqBiblioteca).subscribe({
            next: videojuegos => {
              this.biblioteca = videojuegos.map((v, i) => ({
                videojuego: v,
                esPropio: biblioteca[i].esPropio,
                instalado: idsInstalados.has(v.idJuego)
              }));
            },
            error: () => this.error = 'Error al cargar biblioteca'
          });
        }

        // ---- CARGAR JUEGOS INSTALADOS (PRESTADOS O NO) ----
        if (instalaciones.length > 0) {
          const reqInstalados = instalaciones.map(i =>
            this.videojuegoService.obtenerPorId(i.idJuego)
          );

          forkJoin(reqInstalados).subscribe({
            next: juegos => {
              this.juegosInstalados = juegos;
            },
            error: () => this.error = 'Error al cargar juegos instalados'
          });
        }

        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al cargar datos';
        this.cargando = false;
      }
    });
  }

  instalarJuego(item: BibliotecaVista): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.instalacionService
      .instalarJuego(usuario.idUsuario, item.videojuego.idJuego, false)
      .subscribe({
        next: () => item.instalado = true,
        error: () => this.error = 'No se pudo instalar el juego'
      });
  }

  desinstalarJuego(item: BibliotecaVista): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.instalacionService
      .desinstalarJuego(usuario.idUsuario, item.videojuego.idJuego)
      .subscribe({
        next: () => item.instalado = false,
        error: () => this.error = 'No se pudo desinstalar el juego'
      });
  }

  desinstalarJuegoDirecto(juego: Videojuego): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.instalacionService
      .desinstalarJuego(usuario.idUsuario, juego.idJuego)
      .subscribe({
        next: () => {
          this.juegosInstalados =
            this.juegosInstalados.filter(j => j.idJuego !== juego.idJuego);

          const b = this.biblioteca.find(b => b.videojuego.idJuego === juego.idJuego);
          if (b) b.instalado = false;
        },
        error: () => this.error = 'No se pudo desinstalar el juego'
      });
  }
}




