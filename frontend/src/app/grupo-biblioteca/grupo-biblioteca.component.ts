import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { BibliotecaGrupoService } from '../services/biblioteca-grupo.service';
import { AuthService } from '../services/auth.service';
import { InstalacionJuegoService } from '../services/instalacion-juego.service';

@Component({
  selector: 'app-grupo-biblioteca',
  standalone: true,
  imports: [CommonModule, RouterModule],
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
    private bibliotecaGrupoService: BibliotecaGrupoService,
    private instalacionService: InstalacionJuegoService
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
          this.juegos = data.map(j => ({
            ...j,
            prestado: false,      
            instalado: false     
          }));

          this.juegos.forEach(juego => {
            this.instalacionService
              .estaInstalado(usuario.idUsuario, juego.idJuego)
              .subscribe(resp => {
                juego.instalado = resp.instalado;
              });
          });          

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
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;
  
    this.instalacionService.instalarJuego(
      usuario.idUsuario,
      juego.idJuego,
      true,
      juego.idDueno // ✅ ESTE ES EL CAMBIO CLAVE
    )
    .subscribe({
      next: () => {
        juego.instalado = true;
        juego.prestado = true;
      },
      error: () => {
        this.mensaje = 'No se pudo instalar el juego';
      }
    });
  }
  

  desinstalarJuego(juego: any): void {
    const usuario = this.authService.obtenerSesion();
    if (!usuario) return;
  
    this.instalacionService
      .desinstalarJuego(usuario.idUsuario, juego.idJuego)
      .subscribe({
        next: () => {
          juego.instalado = false;
          juego.prestado = false; // vuelve a estado no instalado
        },
        error: () => {
          this.mensaje = 'No se pudo desinstalar el juego';
        }
      });
  }
  
}



