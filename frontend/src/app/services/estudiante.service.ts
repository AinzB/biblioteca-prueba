// src/app/services/estudiante.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estudiante } from '../models/estudiante.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EstudianteService {
  private readonly baseUrl = `${environment.apiUrl}/api/students`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(this.baseUrl);
  }

  getById(id: number): Observable<Estudiante> {
    return this.http.get<Estudiante>(`${this.baseUrl}/${id}`);
  }

  searchByStudentCode(code: string): Observable<Estudiante[]> {
    const params = new HttpParams().set('studentCode', code);
    return this.http.get<Estudiante[]>(this.baseUrl, { params });
  }

  create(est: Pick<Estudiante, 
    'studentCode' | 
    'firstName' | 
    'lastName' | 
    'email' | 
    'phone' | 
    'status'
  >): Observable<Estudiante> {
    return this.http.post<Estudiante>(this.baseUrl, est);
  }

  update(
    id: number,
    est: Pick<Estudiante, 
      'studentCode' | 
      'firstName' | 
      'lastName' | 
      'email' | 
      'phone' | 
      'status'
    >
  ): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, est);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
