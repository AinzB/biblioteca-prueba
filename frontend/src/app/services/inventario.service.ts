// src/app/services/inventario.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inventario } from '../models/inventario.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private readonly baseUrl = `${environment.apiUrl}/api/bookloans`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(this.baseUrl);
  }

  getById(id: number): Observable<Inventario> {
    return this.http.get<Inventario>(`${this.baseUrl}/${id}`);
  }

  getByCopyId(copyId: number): Observable<Inventario> {
    return this.http.get<Inventario>(`${this.baseUrl}/copy/${copyId}`);
  }

  getByBookId(bookId: number): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(`${this.baseUrl}/book/${bookId}`);
  }

  create(loan: Pick<Inventario,
    'bookCopyId' |
    'studentId' |
    'dueDate'    |
    'returnDate'
  >): Observable<Inventario> {
    return this.http.post<Inventario>(this.baseUrl, loan);
  }

  update(
    id: number,
    loan: Pick<Inventario,
      'bookCopyId' |
      'studentId' |
      'dueDate'    |
      'returnDate'
    >
  ): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, loan);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchByBookTitle(title: string): Observable<Inventario[]> {
    const params = new HttpParams().set('title', title);
    return this.http.get<Inventario[]>(this.baseUrl, { params });
  }
}
