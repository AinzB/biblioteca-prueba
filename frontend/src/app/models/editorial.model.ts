export interface Editorial {
  id: number;
  name: string;
  phone?: string;
  email?: string;
  address?: string;
  createdAt: Date | null;
}
