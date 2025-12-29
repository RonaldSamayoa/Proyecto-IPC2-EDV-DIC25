import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarGamerComponent } from '../shared/navbar-gamer/navbar-gamer.component';

@Component({
  selector: 'app-dashboard-gamer',
  standalone: true,
  imports: [CommonModule, NavbarGamerComponent],
  templateUrl: './dashboard-gamer.component.html',
  styleUrls: ['./dashboard-gamer.component.css']
})
export class DashboardGamerComponent {}

