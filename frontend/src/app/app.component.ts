import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Sistema de biblioteca';
  paginaActual:string = '/autores';

  constructor(private router: Router, private route: ActivatedRoute){
    this.router.events.subscribe(() => {
      this.paginaActual = this.router.url;
    });
  }
}
