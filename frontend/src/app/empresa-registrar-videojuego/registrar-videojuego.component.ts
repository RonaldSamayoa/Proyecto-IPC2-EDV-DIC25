import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VideojuegoService } from '../services/videojuego.service';
import { AuthService } from '../services/auth.service';
import { VideojuegoCreate } from '../models/videojuego-create.model';

@Component({
  selector: 'app-registrar-videojuego',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registrar-videojuego.component.html',
  styleUrls: ['./registrar-videojuego.component.css']
})
export class RegistrarVideojuegoComponent {

  videojuego: VideojuegoCreate = {
    titulo: '',
    descripcion: '',
    precio: 0,
    requisitosMinimos: '',
    clasificacionEdad: '',
    fechaLanzamiento: '',
    idEmpresa: 0
  };

  cargando = false;
  mensaje = '';
  error = '';

  constructor(
    private videojuegoService: VideojuegoService,
    private authService: AuthService
  ) {}

  registrar(): void {
    this.error = '';
    this.mensaje = '';

    const usuario = this.authService.obtenerSesion();
    if (!usuario) {
      this.error = 'Sesión no válida';
      return;
    }
    
    this.videojuego.idEmpresa = usuario.idUsuario; // temporal

    this.cargando = true;

    this.videojuegoService.registrar(this.videojuego).subscribe({
      next: () => {
        this.mensaje = 'Videojuego registrado correctamente';
        this.cargando = false;
        this.resetForm();
      },
      error: () => {
        this.error = 'No se pudo registrar el videojuego';
        this.cargando = false;
      }
    });
  }

  private resetForm(): void {
    this.videojuego = {
      titulo: '',
      descripcion: '',
      precio: 0,
      requisitosMinimos: '',
      clasificacionEdad: '',
      fechaLanzamiento: '',
      idEmpresa: 0
    };
  }
}
