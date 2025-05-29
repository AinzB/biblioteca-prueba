import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Estudiante } from '../models/estudiante.model';
import { EstudianteService } from '../services/estudiante.service';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-estudiantes',
  imports: [CommonModule, FormsModule],
  templateUrl: './estudiantes.component.html',
  styleUrl: './estudiantes.component.scss'
})
export class EstudiantesComponent implements OnInit {
  public estudiantes: Estudiante[] = [];
    
  public nuevoEstudiante: Estudiante = {
    id: 0,
    studentCode: '',
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    status: '',
    createdAt: null
  };

  public nameSearchFilter: string = '';
  public loading: boolean = false;
  public onUpdate: boolean = false;

  constructor(private estudianteService: EstudianteService, private alerService: AlertService) { }

  ngOnInit(): void {
    this.getAll();
  }

  public preSave(): void {
    this.loading = true;

    if( this.nuevoEstudiante.firstName.trim() === '' || 
        this.nuevoEstudiante.lastName.trim() === '' ||
        this.nuevoEstudiante.studentCode.trim() === '' ||
        this.nuevoEstudiante.email.trim() === '' ||
        this.nuevoEstudiante.phone.trim() === '' ||
        this.nuevoEstudiante.status.trim() === '') 
    {
      this.alerService.showError('Fallo al ingresar estudiante, datos incompletos.');
      this.loading = false;
      return;
    }

    if( this.onUpdate ) {
      if( this.nuevoEstudiante.id <= 0 ) {
        this.alerService.showError('El ID del estudiante es inválido.');
        this.loading = false;
        return;
      }
      this.actualizar();
    } else {
      this.guardar();
    }
  }

  private guardar(): void {
    this.estudianteService.create({
      studentCode: this.nuevoEstudiante.studentCode,
      firstName: this.nuevoEstudiante.firstName,
      lastName: this.nuevoEstudiante.lastName,
      email: this.nuevoEstudiante.email,
      phone: this.nuevoEstudiante.phone,
      status: this.nuevoEstudiante.status
    }).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Estudiante creado correctamente');
        this.limpiarCampos();

        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al crear el estudiante.');
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
    this.estudianteService.update(this.nuevoEstudiante.id, {
      studentCode: this.nuevoEstudiante.studentCode,
      firstName: this.nuevoEstudiante.firstName,
      lastName: this.nuevoEstudiante.lastName,
      email: this.nuevoEstudiante.email,
      phone: this.nuevoEstudiante.phone,
      status: this.nuevoEstudiante.status
    }).subscribe({
      next: (data) => {
        this.alerService.showSuccess('Usuario actualizado correctamente');
        this.limpiarCampos();
        this.onUpdate = false;

        if( this.nameSearchFilter.trim() !== '') {
          this.getByName();
        } else {
          this.getAll();
        }
      },
      error: (error) => {
        this.alerService.showError('Error al actualizar el estudiante.');
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getAll(): void {
    this.loading = true;
    this.estudianteService.getAll().subscribe({
      next: (data) => {
        this.estudiantes = data;
        this.alerService.showSuccess('Estudiantes cargados correctamente');
      },
      error: (error) => {
        this.estudiantes = [];
        this.alerService.showError('Error al cargar los estudiantes.');
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  public getByName(): void {
    this.loading = true;
    this.estudianteService.searchByStudentCode(this.nameSearchFilter).subscribe({
      next: (data) => {
        this.estudiantes = data;
        this.alerService.showSuccess('Estudiantes filtrados por codigo correctamente');
      },
      error: (error) => {
        this.estudiantes = [];
        this.alerService.showError('Error al filtrar los estudiantes por codigo.');
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

  public preparaEditar(estudiante: Estudiante): void {
    this.onUpdate = true;
    this.nuevoEstudiante = { ...estudiante };
  }
  
  private limpiarCampos(): void {
    this.nuevoEstudiante = {
      id: 0,
      studentCode: '',
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      status: '',
    };
  }
}
