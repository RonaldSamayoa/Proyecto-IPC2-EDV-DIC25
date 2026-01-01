import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar-gamer',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar-gamer.component.html',
  styleUrls: ['./navbar-gamer.component.css']
})
export class NavbarGamerComponent {

  constructor(
    public authService: AuthService, 
    private router: Router
  ) {}

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }
}


