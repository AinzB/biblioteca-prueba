// src/app/services/libro.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Libro } from '../models/libro.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LibroService {
  private readonly baseUrl = `${environment.apiUrl}/api/books`;

  constructor(private http: HttpClient) { }

  uploadImage(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.baseUrl}/uploadImage`, formData, { responseType: 'text' } );
  }

  getAll(title?: string): Observable<Libro[]> {
    if (title) {
      return this.searchByTitle(title);
    }
    return this.http.get<Libro[]>(this.baseUrl);
  }

  getById(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.baseUrl}/${id}`);
  }

  searchByTitle(title: string): Observable<Libro[]> {
    const params = new HttpParams().set('title', title);
    return this.http.get<Libro[]>(this.baseUrl, { params });
  }

  create(
    book: Pick<Libro,
      'title' |
      'isbn' |
      'publicationYear' |
      'category' |
      'publisherId' |
      'authorId'
    >,
    file?: File
  ): Observable<Libro> {
    if (file) {
      return this.uploadImage(file).pipe(
        switchMap(imageUrl =>
          this.http.post<Libro>(this.baseUrl, { ...book, imageUrl })
        )
      );
    }
    return this.http.post<Libro>(this.baseUrl, book);
  }

  update(
    id: number,
    book: Pick<Libro,
      'title' |
      'isbn' |
      'publicationYear' |
      'category' |
      'publisherId' |
      'authorId'
    >,
    file?: File
  ): Observable<void> {
    if (file) {
      return this.uploadImage(file).pipe(
        switchMap(imageUrl =>
          this.http.put<void>(`${this.baseUrl}/${id}`, { ...book, imageUrl })
        )
      );
    }
    return this.http.put<void>(`${this.baseUrl}/${id}`, book);
  }

  filter(bookId: number, publisherId: number): Observable<Libro[]> {
    const params = new HttpParams()
      .set('bookId', bookId.toString())
      .set('publisherId', publisherId.toString());
    return this.http.get<Libro[]>(`${this.baseUrl}/filter`, { params });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
