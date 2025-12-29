import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard-empresa',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-empresa.component.html',
  styleUrls: ['./dashboard-empresa.component.css']
})
export class DashboardEmpresaComponent {
    constructor(
        private authService: AuthService,
        private router: Router
      ) {}
    
      cerrarSesion(): void {
        this.authService.logout();
        this.router.navigate(['']);
      }
}
