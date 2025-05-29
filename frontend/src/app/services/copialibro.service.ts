// src/app/services/copialibro.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Copialibro } from '../models/copialibro.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CopialibroService {
  private readonly baseUrl = `${environment.apiUrl}/api/bookcopies`;

  constructor(private http: HttpClient) { }

  /** Obtiene todas las copias de libro con detalles */
  getAll(): Observable<Copialibro[]> {
    return this.http.get<Copialibro[]>(this.baseUrl);
  }

  /** Obtiene una copia por su ID */
  getById(id: number): Observable<Copialibro> {
    return this.http.get<Copialibro>(`${this.baseUrl}/${id}`);
  }

  /** Busca copias por ID de libro */
  getByBookId(bookId: number): Observable<Copialibro[]> {
    return this.http.get<Copialibro[]>(`${this.baseUrl}/book/${bookId}`);
  }

  searchByTitle(title: string): Observable<Copialibro[]> {
    const params = new HttpParams().set('title', title);
    return this.http.get<Copialibro[]>(this.baseUrl, { params });
  }

  /** Crea una nueva copia */
  create(copy: Pick<Copialibro,
    'bookId' |
    'publisherId' |
    'status' |
    'condition'
  >): Observable<Copialibro> {
    return this.http.post<Copialibro>(this.baseUrl, copy);
  }

  /** Actualiza una copia existente */
  update(
    id: number,
    copy: Pick<Copialibro,
      'bookId' |
      'publisherId' |
      'status' |
      'condition'
    >
  ): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, copy);
  }

  markAsDisponible(copyId: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${copyId}/available`, null);
  }

  /** Elimina una copia por su ID */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
