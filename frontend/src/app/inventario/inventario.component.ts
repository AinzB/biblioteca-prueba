import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AlertService } from '../services/alert.service';
import { Copialibro } from '../models/copialibro.model';
import { CopialibroService } from '../services/copialibro.service';
import { Inventario } from '../models/inventario.model';
import { InventarioService } from '../services/inventario.service';
import { Estudiante } from '../models/estudiante.model';
import { EstudianteService } from '../services/estudiante.service';

@Component({
  selector: 'app-inventario',
  imports: [CommonModule, FormsModule],
  templateUrl: './inventario.component.html',
  styleUrl: './inventario.component.scss'
})
export class InventarioComponent implements OnInit {

  public nameSearchFilter: string = '';
  public loading: boolean = false;
  public onUpdate: boolean = false;
  public today: string = '';

  public estudiantes: Estudiante[] = [];
  public copiaLibros: Copialibro[] = [];
  public nuevoPrestamo: Partial<Inventario> = {};

  constructor(
    private copialibroService: CopialibroService,
    private alerService: AlertService,
    private estudianteService: EstudianteService,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    const now = new Date();
    this.today = now.toISOString().split('T')[0];

    this.estudianteService.getAll().subscribe(list => this.estudiantes = list);

    this.getAll();
  }

  public getAll(): void {
    this.loading = true;
    this.copialibroService.getAll().subscribe({
      next: (data) => {
        this.copiaLibros = data;
        this.alerService.showSuccess('Libros cargados correctamente');
      },
      error: (error) => {
        this.copiaLibros = [];
        this.alerService.showError('Error al cargar los libros.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getByName(): void {
    this.loading = true;
    this.copialibroService.searchByTitle(this.nameSearchFilter).subscribe({
      next: (data) => {
        this.copiaLibros = data;
        this.alerService.showSuccess('Libros filtrados por titulo correctamente');
      },
      error: (error) => {
        this.copiaLibros = [];
        this.alerService.showError('Error al filtrar los libros por titulo.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public preSave(): void {
    if (!this.nuevoPrestamo.bookCopyId) {
      this.alerService.showError('Debe seleccionar una copia de libro.');
      return;
    }
    if (!this.nuevoPrestamo.studentId) {
      this.alerService.showError('Debe seleccionar un estudiante.');
      return;
    }
    if (!this.nuevoPrestamo.dueDate) {
      this.alerService.showError('Debe ingresar una fecha de vencimiento.');
      return;
    }

    this.save();
  }

  public save(): void {
    this.loading = true;
    this.inventarioService.create({
      bookCopyId: this.nuevoPrestamo.bookCopyId ?? 0,
      studentId: this.nuevoPrestamo.studentId ?? 0,
      dueDate: this.nuevoPrestamo.dueDate ?? null,
      returnDate: this.nuevoPrestamo.returnDate ?? null
    }).subscribe({
      next: () => {
        this.alerService.showSuccess('Prestamo creado correctamente');
        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al crear el prestamo.');
        this.loading = false;
      }
    });
  }

  public onLoan(copiaLibro: Copialibro): void {
    this.nuevoPrestamo.authorName = copiaLibro.authorName;
    this.nuevoPrestamo.bookCopyId = copiaLibro.id;
    this.nuevoPrestamo.bookTitle = copiaLibro.bookTitle;
    this.nuevoPrestamo.bookImageUrl = copiaLibro.bookImageUrl;
    this.nuevoPrestamo.publisherName = copiaLibro.publisherName;
    this.nuevoPrestamo.publisherName = copiaLibro.publisherName;
    
    if(this.estudiantes.length > 0 ) {this.nuevoPrestamo.studentId = this.estudiantes[0].id; }
  }

  public onDetailCopyLoan(copia: Copialibro): void {
    this.loading = true;
    this.inventarioService.getByCopyId(copia.id).subscribe({
      next: (data) => {
        this.nuevoPrestamo = data;
        this.alerService.showSuccess('Mostrando detalle del prestamo.');
      },
      error: (error) => {
        this.alerService.showError('Error al cargar los detalles del prestamo.');
        this.loading = false;
        this.limpiarCampos();
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public onReceiveBook(bookCopyId: number): void {
    if(bookCopyId <= 0) {
      this.alerService.showError('Fallo al encontrar el libro a retornar.');
      return;
    }

    this.loading = true;
    this.copialibroService.markAsDisponible(bookCopyId)
    .subscribe({
      next: () => {
        this.alerService.showSuccess('Libro retornado correctamente');
        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al retornar el libro.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
        this.limpiarCampos();
      }
    });
  }

  public setUpdateFalse(): void {
    this.onUpdate = false;
    this.limpiarCampos();
  }

  private limpiarCampos(): void {
    this.nuevoPrestamo = {};
    this.loading = false;
  }

  public limCamp(): void {
    this.limpiarCampos();
    this.onUpdate = false;
  }
}
