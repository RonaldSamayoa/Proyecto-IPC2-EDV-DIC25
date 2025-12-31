import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BibliotecaGrupoService } from '../services/biblioteca-grupo.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-grupo-biblioteca',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './grupo-biblioteca.component.html',
  styleUrls: ['./grupo-biblioteca.component.css']
})
export class GrupoBibliotecaComponent implements OnInit {

  idGrupo!: number;
  juegos: any[] = [];
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private bibliotecaGrupoService: BibliotecaGrupoService
  ) {}

  ngOnInit(): void {
    this.idGrupo = Number(this.route.snapshot.paramMap.get('idGrupo'));
    this.cargarBiblioteca();
  }

  cargarBiblioteca(): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;

    this.bibliotecaGrupoService
      .listarPorGrupo(this.idGrupo, usuario.idUsuario)
      .subscribe({
        next: data => {
          this.juegos = data.map(juego => ({
            ...juego,
            prestado: false // SOLO cambia cuando se instale
          }));

          if (this.juegos.length === 0) {
            this.mensaje = 'No hay juegos compartidos en este grupo';
          }
        },
        error: () => {
          this.mensaje = 'Error al cargar biblioteca compartida';
        }
      });
  }

  verDetalle(idJuego: number): void {
    this.router.navigate(['/videojuego', idJuego]);
  }

  instalarJuego(juego: any): void {
    juego.prestado = true;
  }
}


