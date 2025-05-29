import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Category } from '../enums/category.enum';
import { Libro } from '../models/libro.model';
import { LibroService } from '../services/libro.service';
import { Editorial } from '../models/editorial.model';
import { EditorialService } from '../services/editorial.service';
import { Autor } from '../models/autor.model';
import { AutorService } from '../services/autor.service';
import { AlertService } from '../services/alert.service';
import { Copialibro } from '../models/copialibro.model';
import { CopialibroService } from '../services/copialibro.service';
import { Inventario } from '../models/inventario.model';
import { InventarioService } from '../services/inventario.service';

@Component({
  selector: 'app-libros',
  imports: [CommonModule, FormsModule],
  templateUrl: './libros.component.html',
  styleUrl: './libros.component.scss'
})
export class LibrosComponent implements OnInit {

  librosCategories = Object.values(Category);

  public libros: Libro[] = [];
  public publishers: Editorial[] = [];
  public autores: Autor[] = [];
  public prestamos: Inventario[] = [];
      
  public nuevoLibro: Partial<Libro> = {};
  public nuevaCopiaLibro: Partial<Copialibro> = {};
  public selectedFile: File | null = null;

  public nameSearchFilter: string = '';
  public loading: boolean = false;
  public onUpdate: boolean = false;

  constructor(
    private libroService: LibroService, 
    private editorialService: EditorialService, 
    private alerService: AlertService,
    private autorService: AutorService,
    private copialibroService: CopialibroService,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    this.getAll();
    this.editorialService.getAll().subscribe(list => this.publishers = list);
    this.autorService.getAll().subscribe(list => this.autores = list);
  }

  public getAll(): void {
    this.loading = true;
    this.libroService.getAll().subscribe({
      next: (data) => {
        this.libros = data;
        this.alerService.showSuccess('Libros cargados correctamente');
      },
      error: (error) => {
        this.libros = [];
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
    this.libroService.searchByTitle(this.nameSearchFilter).subscribe({
      next: (data) => {
        this.libros = data;
        this.alerService.showSuccess('Libros filtrados por titulo correctamente');
      },
      error: (error) => {
        this.libros = [];
        this.alerService.showError('Error al filtrar los libros por titulo.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getCountByBookAndPublisherId(libro: Libro): void {
    this.loading = true;
    this.libroService.filter(libro.id, libro.publisherId).subscribe({
      next: (data) => {
        this.nuevoLibro = data[0];
        this.getLoanByBookId(this.nuevoLibro.id ?? 0);
        this.alerService.showSuccess('Mostrando detalles del libro seleccionado.');
      },
      error: (error) => {
        this.limpiarCampos();
        this.alerService.showError('Error al fcargar el libro seleccionado.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getLoanByBookId(bookId: number): void {
    if (bookId <= 0) {
      this.alerService.showError('ID de libro inválido.');
      this.loading = false;
      return;
    }

    this.loading = true;
    this.inventarioService.getByBookId(bookId).subscribe({
      next: (data) => {
        this.prestamos = data;
      },
      error: (error) => {
        this.alerService.showError('Error al obtener las copias prestadas.');
        console.error(error);
        this.loading = false;
        this.prestamos = [];
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public preSave(): void {
    this.loading = true;
    if (this.nuevoLibro.title?.trim() === '' || 
        this.nuevoLibro.isbn?.trim() === '' ||
        this.nuevoLibro.publicationYear === 0 ||
        this.nuevoLibro.category === undefined ||
        this.nuevoLibro.publisherId === undefined ||
        this.nuevoLibro.authorId === undefined ||
        (!this.selectedFile && !this.onUpdate)
      ) 
    {
      this.alerService.showError('Fallo al ingresar libro, datos incompletos.');
      this.loading = false;
      return;
    }

    if (this.onUpdate) {
      if (this.nuevoLibro.id === undefined || this.nuevoLibro.id <= 0) {
        this.alerService.showError('El ID del libro es inválido.');
        this.loading = false;
        return;
      }
      this.actualizar();
    } else {
      this.guardar();
    }
  }

  private guardar(): void {
    this.libroService.create(
      {
        title: this.nuevoLibro.title!,
        isbn: this.nuevoLibro.isbn!,
        publicationYear: this.nuevoLibro.publicationYear!,
        category: this.nuevoLibro.category!,
        publisherId: this.nuevoLibro.publisherId!,
        authorId: this.nuevoLibro.authorId!
      }, this.selectedFile ?? undefined
  ).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Libro creado correctamente');
        this.limpiarCampos();

        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al crear el libro.');
        this.loading = false;
        console.log(error);
        
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  private actualizar(): void {
    this.alerService.showSuccess('Actualizando');
    this.libroService.update(this.nuevoLibro.id ?? 0, 
      {
        title: this.nuevoLibro.title!,
        isbn: this.nuevoLibro.isbn!,
        publicationYear: this.nuevoLibro.publicationYear!,
        category: this.nuevoLibro.category!,
        publisherId: this.nuevoLibro.publisherId!,
        authorId: this.nuevoLibro.authorId!
      }, this.selectedFile ?? undefined
  ).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Libro actualizado correctamente');
        this.limpiarCampos();
        this.onUpdate = false;

        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al actualizar el libro.');
        this.loading = false;
        console.log(error);
        
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public guardarCopia(): void {
    if (!this.nuevaCopiaLibro.bookId || !this.nuevaCopiaLibro.publisherId) {
      this.alerService.showError('Debe seleccionar libro y editorial.');
      return;
    }

    if(!this.nuevaCopiaLibro.status || !this.nuevaCopiaLibro.condition) {
      this.alerService.showError('Debe seleccionar estado y condición de la copia.');
      return;
    }

    this.loading = true;
    this.copialibroService.create({
      bookId: this.nuevaCopiaLibro.bookId!,
      publisherId: this.nuevaCopiaLibro.publisherId!,
      status: this.nuevaCopiaLibro.status!,
      condition: this.nuevaCopiaLibro.condition!
    }).subscribe({
      next: (copia) => {
        this.alerService.showSuccess('Copia registrada correctamente');
        this.limpiarCamposCopia();
      },
      error: (err) => {
        console.error(err);
        this.alerService.showError('Error al registrar la copia');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  handleSelectedBookForCopy(libro: Libro): void {
    this.nuevaCopiaLibro.bookId = libro.id;
    this.nuevaCopiaLibro.bookTitle = libro.title;
    this.nuevaCopiaLibro.bookImageUrl = libro.imageUrl;
    this.nuevaCopiaLibro.publisherId = libro.publisherId;
    this.nuevaCopiaLibro.publisherName = libro.publisherName;
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  public setUpdateFalse(): void {
    this.onUpdate = false;
    this.limpiarCampos();
  }

  public preparaEditar(libro: Libro): void {
    this.onUpdate = true;
    this.nuevoLibro = { ...libro };
  }

  public limCamp(): void {
    this.limpiarCampos();
    this.onUpdate = false;
  }
  
  private limpiarCampos(): void {
    this.nuevoLibro = {
      id: 0,
      title: '',
      isbn: '',
      publicationYear: 0,
      category: '',
      imageUrl: '',
      publisherId: 0,
      publisherName: '',
      createdAt: null
    };

    this.selectedFile = null;
  }

  private limpiarCamposCopia(): void {
    this.nuevaCopiaLibro = {
      bookId: 0,
      publisherId: 0,
      status: 'DISPONIBLE',
      condition: 'BUENA'
    };
  }
}
