import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';
import { LoginRequest } from '../models/usuario.model';

@Component({
  selector: 'app-login',
  standalone: true,              
  imports: [CommonModule, FormsModule], 
  templateUrl: './login.component.html'
})
export class LoginComponent {

  datosLogin: LoginRequest = {
    correo: '',
    password: ''
  };

  mensaje = '';

  constructor(private authService: AuthService) {}

  login(): void {
    console.log('Intentando login...', this.datosLogin);
  
    this.authService.login(this.datosLogin).subscribe({
      next: (usuario) => {
        console.log('Login OK:', usuario);
        this.authService.guardarSesion(usuario);
        this.mensaje = 'Login correcto';
      },
      error: (err) => {
        console.error('Error login:', err);
        this.mensaje = 'Credenciales inválidas';
      }
    });
  }
  
}

