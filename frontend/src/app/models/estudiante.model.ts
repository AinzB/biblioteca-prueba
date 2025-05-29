export interface Estudiante {
  id: number;
  studentCode: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  status: string;
  createdAt?: Date | null;
}
