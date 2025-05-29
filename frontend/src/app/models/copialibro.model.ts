export interface Copialibro {
  id: number;
  bookId: number;
  bookTitle?: string;
  bookImageUrl?: string;
  publisherId: number;
  publisherName?: string;
  authorId: number;
  authorName?: string;
  status: 'DISPONIBLE' | 'PRESTADO' | 'PERDIDO';
  condition: 'EXCELENTE' | 'BUENA' | 'MALA';
  createdAt?: Date | null;
}
