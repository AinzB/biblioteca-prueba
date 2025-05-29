import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Autor } from '../models/autor.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AutorService {
  private readonly baseUrl = `${environment.apiUrl}/api/authors`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Autor[]> {
    return this.http.get<Autor[]>(this.baseUrl);
  }

  getById(id: number): Observable<Autor> {
    return this.http.get<Autor>(`${this.baseUrl}/${id}`);
  }

  searchByName(name: string): Observable<Autor[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Autor[]>(this.baseUrl, { params });
  }

  create(autor: Pick<Autor, 'name' | 'biography'>): Observable<Autor> {
    return this.http.post<Autor>(this.baseUrl, autor);
  }

  update(id: number, autor: Pick<Autor, 'name' | 'biography'>): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, autor);
  }
}
