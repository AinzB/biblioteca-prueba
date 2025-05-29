export interface Libro {
  id: number;
  title: string;
  isbn: string;
  publicationYear: number;
  category: string;
  imageUrl: string;
  publisherId: number;
  authorId: number;
  publisherName: string;
  authorName: string;
  createdAt?: Date | null;
  countExcelente?: number;
  countBuena?: number;
  countMala?: number;
}
