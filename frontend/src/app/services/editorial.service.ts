import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Editorial } from '../models/editorial.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EditorialService {
  private readonly baseUrl = `${environment.apiUrl}/api/publishers`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Editorial[]> {
    return this.http.get<Editorial[]>(this.baseUrl);
  }

  getById(id: number): Observable<Editorial> {
    return this.http.get<Editorial>(`${this.baseUrl}/${id}`);
  }

  searchByName(name: string): Observable<Editorial[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Editorial[]>(this.baseUrl, { params });
  }

  create(editorial: Pick<Editorial, 'name' | 'phone' | 'email' | 'address'>): Observable<Editorial> {
    return this.http.post<Editorial>(this.baseUrl, editorial);
  }

  update(id: number, editorial: Pick<Editorial, 'name' | 'phone' | 'email' | 'address'>): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, editorial);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
