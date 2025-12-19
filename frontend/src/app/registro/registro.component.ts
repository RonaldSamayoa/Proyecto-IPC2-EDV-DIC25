import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { RegistroRequest } from '../models/usuario.model';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro.component.html'
})
export class RegistroComponent {

  datosRegistro: RegistroRequest = {
    nickname: '',
    correo: '',
    password: '',
    fechaNacimiento: '',
    pais: '',
    bibliotecaPublica: true
  };

  mensaje = '';

  constructor(private authService: AuthService) {}

  registrar(): void {
    this.authService.registrar(this.datosRegistro).subscribe({
      next: () => {
        this.mensaje = 'Usuario registrado correctamente';
      },
      error: () => {
        this.mensaje = 'Error al registrar usuario';
      }
    });
  }
}
