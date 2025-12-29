import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar-gamer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar-gamer.component.html',
  styleUrls: ['./navbar-gamer.component.css']
})
export class NavbarGamerComponent {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }
}
