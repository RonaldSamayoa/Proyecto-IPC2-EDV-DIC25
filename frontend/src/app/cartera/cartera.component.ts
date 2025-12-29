import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CarteraService } from '../services/cartera.service';
import { AuthService } from '../services/auth.service';
import { NavbarGamerComponent } from '../shared/navbar-gamer/navbar-gamer.component';

@Component({
  selector: 'app-cartera',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarGamerComponent],
  templateUrl: './cartera.component.html',
  styleUrls: ['./cartera.component.css']
})
export class CarteraComponent implements OnInit {

  saldo = 0;
  monto = 0;
  idUsuario!: number;
  mensaje = '';

  constructor(
    private carteraService: CarteraService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.obtenerSesion();
  
    if (!usuario) {
      // seguridad extra, nunca debería pasar
      return;
    }
  
    this.idUsuario = usuario.idUsuario;
    this.cargarSaldo();
  }
  

  cargarSaldo(): void {
    this.carteraService.obtenerCartera(this.idUsuario).subscribe(c => {
      this.saldo = c.saldo;
    });
  }

  recargar(): void {
    if (this.monto <= 0) return;

    this.carteraService.recargar(this.idUsuario, this.monto).subscribe({
      next: () => {
        this.mensaje = 'Recarga realizada';
        this.monto = 0;
        this.cargarSaldo();
      },
      error: () => {
        this.mensaje = 'Error al recargar';
      }
    });
  }
}
