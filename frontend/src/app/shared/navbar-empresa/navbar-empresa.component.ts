import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar-empresa',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar-empresa.component.html',
  styleUrls: ['./navbar-empresa.component.css']
})
export class NavbarEmpresaComponent {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }
}
