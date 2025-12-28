import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ApiConfig } from '../config/api.config';

@Component({
  selector: 'app-registro-empresa',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro-empresa.component.html',
  styleUrls: ['./registro-empresa.component.css']
})
export class RegistroEmpresaComponent {

  empresa = {
    nombre: '',
    descripcion: ''
  };

  usuario = {
    nickname: '',
    correo: '',
    password: '',
    fechaNacimiento: '',
    pais: ''
  };

  error = '';
  exito = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  registrar() {
    const body = {
      empresa: this.empresa,
      usuario: this.usuario
    };

    this.http.post(`${ApiConfig.BASE_URL}/registro-empresa`, body).subscribe({
      next: () => {
        this.exito = 'Empresa registrada correctamente. Ahora puedes iniciar sesión.';
        this.error = '';

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: () => {
        this.error = 'No se pudo completar el registro.';
        this.exito = '';
      }
    });
  }
}
