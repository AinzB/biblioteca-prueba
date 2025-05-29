import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root',
})

export class AlertService { 
    showError(message: string): void {
        const alertDiv = document.createElement('div');
        alertDiv.classList.add('mensajeAlerta', 'mensajeError'); 

        const alertImg = document.createElement('img');
        alertImg.src = 'error.svg'; 
        alertImg.alt = 'Icono de alerta';

        const alertMessage = document.createElement('p');
        alertMessage.textContent = message; 

        // Agregar los elementos al contenedor
        alertDiv.appendChild(alertImg);
        alertDiv.appendChild(alertMessage);

        if(document.body) {
            document.body.appendChild(alertDiv);

            setTimeout(() => {
                alertDiv.remove(); 
            }, 3000);
        }
    }

    showSuccess(message: string): void {
        const alertDiv = document.createElement('div');
        alertDiv.classList.add('mensajeAlerta', 'mensajeSuccess');

        const alertImg = document.createElement('img');
        alertImg.src = 'success.svg'; 
        alertImg.alt = 'Icono de alerta'; 

        const alertMessage = document.createElement('p');
        alertMessage.textContent = message;

        // Agregar los elementos al contenedor
        alertDiv.appendChild(alertImg);
        alertDiv.appendChild(alertMessage);

        if(document.body) {
            document.body.appendChild(alertDiv); 

            setTimeout(() => {
                alertDiv.remove();
            }, 3000);
        }
    }
}