import { Routes } from '@angular/router';
import { AutoresComponent } from './autores/autores.component';
import { EditorialesComponent } from './editoriales/editoriales.component';
import { EstudiantesComponent } from './estudiantes/estudiantes.component';
import { LibrosComponent } from './libros/libros.component';
import { InventarioComponent } from './inventario/inventario.component';

export const routes: Routes = [
    { path: '', redirectTo: '/autores', pathMatch: 'full' },
    { path: 'autores', component: AutoresComponent },
    { path: 'editoriales', component: EditorialesComponent },
    { path: 'estudiantes', component: EstudiantesComponent },
    { path: 'libros', component: LibrosComponent },
    { path: 'inventario', component: InventarioComponent }
];
