import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
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

  cargando = false;

  constructor(private http: HttpClient) {}

  registrarEmpresa() {
    this.cargando = true;

    const body = {
      empresa: this.empresa,
      usuario: this.usuario
    };

    this.http.post(
      `${ApiConfig.BASE_URL}/registro-empresa`,
      body
    ).subscribe({
      next: () => {
        this.cargando = false;
        alert('Empresa registrada correctamente. Ahora puedes iniciar sesión.');
      },
      error: () => {
        this.cargando = false;
        alert('Error al registrar la empresa');
      }
    });
  }
}



