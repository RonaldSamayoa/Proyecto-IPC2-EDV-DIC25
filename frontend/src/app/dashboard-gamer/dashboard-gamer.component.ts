import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard-gamer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-gamer.component.html',
  styleUrls: ['./dashboard-gamer.component.css']
})
export class DashboardGamerComponent {
    constructor(
        private authService: AuthService,
        private router: Router
      ) {}
    
      cerrarSesion(): void {
        this.authService.logout();
        this.router.navigate(['']);
      }

}
