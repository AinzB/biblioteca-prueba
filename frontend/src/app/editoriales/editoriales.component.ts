import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Editorial } from '../models/editorial.model';
import { EditorialService } from '../services/editorial.service';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-editoriales',
  imports: [CommonModule, FormsModule],
  templateUrl: './editoriales.component.html',
  styleUrl: './editoriales.component.scss'
})
export class EditorialesComponent implements OnInit {
  public editoriales: Editorial[] = [];
  
  public nuevaEditorial: Editorial = {
    id: 0,
    name: '',
    phone: '',
    email: '',
    address: '',
    createdAt: null
  };

  public nameSearchFilter: string = '';
  public loading: boolean = false;
  public onUpdate: boolean = false;

  constructor(private editorialService: EditorialService, private alerService: AlertService) { }

  ngOnInit(): void {
    this.getAll();
  }

  public preSave(): void {
    this.loading = true;

    if( this.nuevaEditorial.name.trim() === '') {
      this.alerService.showError('El nombre es obligatorio.');
      this.loading = false;
      return;
    }

    if( this.onUpdate ) {
      if( this.nuevaEditorial.id <= 0 ) {
        this.alerService.showError('El ID de la editorial es inválido.');
        this.loading = false;
        return;
      }
      this.actualizar();
    } else {
      this.guardar();
    }
  }

  private guardar(): void {
    this.editorialService.create({
      name: this.nuevaEditorial.name,
      phone: this.nuevaEditorial.phone,
      email: this.nuevaEditorial.email,
      address: this.nuevaEditorial.address
    }).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Editorial creada correctamente');
        this.limpiarCampos();

        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al crear la editorial.');
        this.loading = false;
        console.log(error);
        
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  private actualizar(): void {
    this.editorialService.update(this.nuevaEditorial.id, {
      name: this.nuevaEditorial.name,
      phone: this.nuevaEditorial.phone,
      email: this.nuevaEditorial.email,
      address: this.nuevaEditorial.address
    }).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Editorial actualizada correctamente');
        this.limpiarCampos();
        this.onUpdate = false;

        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al actualizar la editorial.');
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getAll(): void {
    this.loading = true;
    this.editorialService.getAll().subscribe({
      next: (data) => {
        this.editoriales = data;
        this.alerService.showSuccess('Editoriales cargadas correctamente');
      },
      error: (error) => {
        this.editoriales = [];
        this.alerService.showError('Error al cargar las editoriales.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getByName(): void {
    this.loading = true;
    this.editorialService.searchByName(this.nameSearchFilter).subscribe({
      next: (data) => {
        this.editoriales = data;
        this.alerService.showSuccess('Editoriales filtradas por nombre correctamente');
      },
      error: (error) => {
        this.editoriales = [];
        this.alerService.showError('Error al filtrar las editoriales por nombre.');
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

  public preparaEditar(editorial: Editorial): void {
    this.onUpdate = true;
    this.nuevaEditorial = { ...editorial };
    
  }
  
  private limpiarCampos(): void {
    this.nuevaEditorial = {
      id: 0,
      name: '',
      phone: '',
      email: '',
      address: '',
      createdAt: null
    };
  }
}
