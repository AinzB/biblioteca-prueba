import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Autor } from '../models/autor.model';
import { AutorService } from '../services/autor.service';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-autores',
  imports: [CommonModule, FormsModule],
  templateUrl: './autores.component.html',
  styleUrl: './autores.component.scss'
})
export class AutoresComponent implements OnInit {
  public autores: Autor[] = [];

  public nuevoAutor: Autor = {
    id: 0,
    name: '',
    biography: '',
    createdAt: null
  };

  public nameSearchFilter: string = '';
  public loading: boolean = false;
  public onUpdate: boolean = false;

  constructor(private autorService: AutorService, private alerService: AlertService) { }

  ngOnInit(): void {
    this.getAutores();
  }

  public guardarAutor(): void {
    this.loading = true;

    if( this.nuevoAutor.name.trim() === '') {
      this.alerService.showError('El nombre es obligatorio.');
      this.loading = false;
      return;
    }

    if( this.onUpdate ) {
      if( this.nuevoAutor.id <= 0 ) {
        this.alerService.showError('El ID del autor es inválido.');
        this.loading = false;
        return;
      }
      this.actualizarAutor();
    } else {
      this.crearAutor();
    }
  }

  private crearAutor(): void {
    this.autorService.create({
      name: this.nuevoAutor.name,
      biography: this.nuevoAutor.biography
    }).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Autor creado correctamente');
        this.limpiarCampos();

        if( this.nameSearchFilter.trim() !== '') {
          this.getAutoresPorNombre();
        } else {
          this.getAutores();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al crear el autor.');
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  private actualizarAutor(): void {
    this.autorService.update(this.nuevoAutor.id, {
      name: this.nuevoAutor.name,
      biography: this.nuevoAutor.biography
    }).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Autor actualizado correctamente');
        this.limpiarCampos();
        this.onUpdate = false;

        if( this.nameSearchFilter.trim() !== '') {
          this.getAutoresPorNombre();
        } else {
          this.getAutores();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al actualizar el autor.');
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getAutores(): void {
    this.loading = true;
    this.autorService.getAll().subscribe({
      next: (data) => {
        this.autores = data;
        this.alerService.showSuccess('Autores cargados correctamente');
      },
      error: (error) => {
        this.autores = [];
        this.alerService.showError('Error al cargar los autores.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getAutoresPorNombre(): void {
    this.loading = true;
    this.autorService.searchByName(this.nameSearchFilter).subscribe({
      next: (data) => {
        this.autores = data;
        this.alerService.showSuccess('Autores filtrados por nombre correctamente');
      },
      error: (error) => {
        this.autores = [];
        this.alerService.showError('Error al filtrar los autores por nombre.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public setUpdateFalse(): void {
    this.onUpdate = false;
    this.limpiarCampos();
  }

  public editarAutor(autor: Autor): void {
    this.onUpdate = true;
    this.nuevoAutor = { ...autor };
    
  }
  
  private limpiarCampos(): void {
    this.nuevoAutor = { id: 0, name: '', biography: '', createdAt: null };
  }
}
