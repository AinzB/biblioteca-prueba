export interface Inventario {
  id: number;
  bookCopyId: number;
  studentId: number;
  loanDate: Date;
  dueDate?: Date | null;
  returnDate?: Date | null;

  // Campos adicionales para display (opcionales)
  bookTitle?: string;
  bookImageUrl?: string;
  studentName?: string;
  condition?: 'EXCELENTE' | 'BUENA' | 'MALA';
  status?: 'DISPONIBLE' | 'PRESTADO' | 'PERDIDO';
  authorName?: string;
  publisherName?: string;
}
