import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { CommonModule } from '@angular/common';
import { NavbarGamerComponent } from '../shared/navbar-gamer/navbar-gamer.component';

@Component({
  selector: 'app-perfil-usuario',
  standalone: true,
  templateUrl: './perfil-usuario.component.html',
  imports: [CommonModule, NavbarGamerComponent],

  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {

  usuario: any;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    const idUsuario = Number(this.route.snapshot.paramMap.get('id'));

    this.usuarioService.getPerfil(idUsuario).subscribe({
      next: data => this.usuario = data,
      error: () => this.error = 'No se pudo cargar el perfil'
    });
  }
}
