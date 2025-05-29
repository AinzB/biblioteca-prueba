Biblioteca-Prueba – Sistema de Gestión de Biblioteca
Descripción del sistema
Aplicación fullstack (backend + frontend) para la gestión de una biblioteca. El sistema permite administrar el catálogo de libros, llevar el inventario de copias físicas de cada libro y gestionar los préstamos de libros a estudiantes. Está compuesto por un backend desarrollado en Java (Spring Boot 2.7, Java 8) y un frontend desarrollado en Angular (con Bootstrap para la interfaz de usuario). En el backend se utiliza MyBatis como ORM para el acceso a datos, con soporte de MyBatis Generator para generar automáticamente los modelos y mappers a partir del esquema de base de datos. La base de datos se versiona y migra mediante Flyway, lo que facilita la creación y actualización de las tablas necesarias. El sistema está diseñado para trabajar con Oracle Database 23c Free (23ai Free) como motor de base de datos. Funcionalidades principales:

Catálogo de libros: permite registrar, consultar y actualizar información de libros (título, autor, categoría, etc.).
Inventario de copias: lleva el control de copias físicas de cada libro en la biblioteca, incluyendo disponibilidad y estado.
Préstamos a estudiantes: gestiona el préstamo y devolución de libros a los estudiantes, registrando qué estudiante tiene cada libro y la fecha de devolución esperada.

Requisitos del sistema

A continuación se listan los requisitos previos para poder compilar y ejecutar el proyecto biblioteca-prueba en un entorno local:
Java JDK 8 – Instalado y configurado en el PATH del sistema.

Oracle Database 23c Free (23ai Free) – Instalado y ejecutándose localmente. Se espera un servicio de base de datos accesible en localhost:1521/FREEPDB1.
Usuario de base de datos Oracle configurado: Debe existir un usuario Oracle para la aplicación (por defecto, usuario appadmin con contraseña secret) con los permisos necesarios para crear tablas, secuencias y otros objetos de esquema.

Node.js y npm – Instalados (se recomienda Node 14+ o una versión LTS reciente, junto con npm correspondiente) para poder instalar y ejecutar el frontend.
Angular CLI – Instalado globalmente (npm install -g @angular/cli) para facilitar comandos de desarrollo frontend.
Maven – No es obligatorio instalar Maven manualmente, ya que el proyecto incluye Maven Wrapper (mvnw), pero opcionalmente puede utilizar Maven 3.x instalado en el sistema.

Además, se recomienda contar con una herramienta cliente SQL (por ejemplo SQL*Plus, SQL Developer o similar) para crear el usuario de base de datos y verificar las tablas, y un navegador web moderno para acceder a la aplicación frontend.

Estructura del repositorio

El repositorio está organizado en dos directorios principales, uno para el backend y otro para el frontend, junto con archivos de configuración y el presente README:

biblioteca-prueba/
├── backend/               # Proyecto Spring Boot (Java + Maven)
│   ├── mvnw, mvnw.cmd     # Maven Wrapper para ejecutar Maven sin instalarlo
│   ├── pom.xml            # Archivo de configuración de Maven (dependencias, plugins)
│   └── src/               # Código fuente del backend (Java + recursos)
│       ├── main/
│       │   ├── java/      # Código fuente Java (controladores, servicios, mappers MyBatis, etc.)
│       │   └── resources/ # Recursos (application.properties, scripts de Flyway, mappers XML de MyBatis)
│       │       └── db/migration/  # Scripts SQL de migración de base de datos (Flyway)
│
├── frontend/              # Proyecto Angular (cliente web)
│   ├── angular.json       # Configuración del proyecto Angular CLI
│   ├── package.json       # Dependencias y scripts del proyecto frontend
│   └── src/               # Código fuente del frontend (componentes, servicios, vistas, estilos)
│       └── app/           # Componentes principales de la aplicación Angular
└── README.md              # Documentación del proyecto (este archivo)

Nota: Dentro de backend/src/main/resources se encuentra el archivo application.properties, que contiene la configuración de conexión a la base de datos (URL, usuario, contraseña) y otras propiedades de la aplicación. Asegúrese de revisar este archivo si necesita cambiar la configuración por defecto (por ejemplo, credenciales de base de datos o puerto del servidor).

Instrucciones de instalación y ejecución

A continuación se detallan los pasos para poner en marcha el sistema biblioteca-prueba en un entorno local:

Clonar el repositorio:
Clone este repositorio en su máquina local usando Git. En una terminal, ejecute:
git clone https://github.com/usuario/biblioteca-prueba.git
Esto creará una carpeta biblioteca-prueba/ con el contenido del proyecto. Luego, ingrese a la carpeta del proyecto:
cd biblioteca-prueba

Preparar la base de datos Oracle:
Asegúrese de tener Oracle Database 23c Free instalado y en ejecución.
Cree la base de datos o Pluggable DB: Si está usando Oracle 23c Free, por defecto se crea una base de datos FREEPDB1. Verifique que esta instancia (pluggable database) esté abierta y accesible.
Crear el usuario de la aplicación: Conéctese a Oracle como un usuario administrador (por ejemplo, SYS o SYSTEM) y ejecute comandos SQL para crear el usuario appadmin y otorgarle permisos. Por ejemplo:
CREATE USER appadmin IDENTIFIED BY secret;
GRANT CONNECT, RESOURCE TO appadmin;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW, CREATE PROCEDURE TO appadmin;
Estas sentencias crean el usuario con la contraseña secret y le otorgan permisos básicos para conectarse y crear objetos de esquema. (Ajuste los privilegios según sus políticas; en entornos de desarrollo puede simplificar otorgando roles predefinidos como RESOURCE).
Actualizar credenciales si es necesario: Si decide usar un nombre de usuario o contraseña diferente, o una instancia de Oracle distinta, edite el archivo backend/src/main/resources/application.properties para actualizar spring.datasource.url, spring.datasource.username y spring.datasource.password con los valores correspondientes. Asegúrese de que la URL de conexión apunte a su instancia (por ejemplo, jdbc:oracle:thin:@localhost:1521/FREEPDB1).
Verificar la conexión: Antes de continuar, puede probar que el usuario appadmin puede conectarse a la base de datos utilizando su herramienta SQL preferida. No es necesario crear tablas manualmente; el siguiente paso (migraciones Flyway) se encargará de eso.

Compilar y ejecutar el backend (Spring Boot):

Con la base de datos lista, proceda a levantar el servidor backend:
Abra una terminal y navegue al directorio backend/ del proyecto:
cd biblioteca-prueba/backend
Compilar el proyecto: Ejecute el comando Maven Wrapper para compilar y ejecutar las pruebas (opcional):
./mvnw clean install
Nota: Si está en Windows, use mvnw.cmd en lugar de ./mvnw. Si no desea ejecutar pruebas en cada compilación, puede usar ./mvnw clean package -DskipTests.
Ejecutar las migraciones de base de datos y levantar el servidor: Ejecute la aplicación Spring Boot con Maven:
./mvnw spring-boot:run

Al iniciar, Spring Boot se conectará a la base de datos Oracle usando la configuración proporcionada. Flyway aplicará automáticamente las migraciones SQL encontradas en classpath:db/migration (por ejemplo, archivos V1__*.sql, V2__*.sql, etc.) para crear las tablas y datos iniciales requeridos por la aplicación. En los logs de la consola debería ver mensajes de Flyway indicando la ejecución de las migraciones exitosamente.

Confirmar ejecución: Una vez iniciado, el backend estará escuchando por defecto en el puerto 8080. Debería ver en la consola un mensaje similar a Started BibliotecaPruebaApplication in [segundos] seconds indicando que la aplicación se inició correctamente.
Opcional: Puede verificar rápidamente el funcionamiento accediendo (por navegador o herramienta como cURL/Postman) a alguna URL de la API, por ejemplo http://localhost:8080/ o alguna ruta expuesta. Es posible que el backend exponga una API REST (por ejemplo, en rutas /api/libros, /api/prestamos, etc.), la cual será consumida por el frontend.

Instalar y ejecutar el frontend (Angular):

Ahora proceda con la aplicación frontend para la interfaz de usuario:
En otra terminal, navegue al directorio frontend/ del proyecto:
cd biblioteca-prueba/frontend

Instalar dependencias: Ejecute el gestor de paquetes npm para instalar todas las dependencias definidas en package.json:
npm install
Esto descargará todos los módulos necesarios (incluyendo Angular, Bootstrap, etc.).
Configurar entorno (si es necesario): Por defecto, la aplicación Angular podría tener configurado en src/environments/environment.ts la URL base de la API (por ejemplo, http://localhost:8080/api). Verifique si necesita ajustar esta URL según la configuración de su backend. En la mayoría de los casos de desarrollo local, no hará falta cambiar nada si se usa localhost:8080 para el backend.
Iniciar la aplicación Angular: Una vez instaladas las dependencias, inicie el servidor de desarrollo de Angular:
ng serve --open

El comando anterior compilará la aplicación y levantará un servidor de desarrollo. La opción --open abrirá automáticamente su navegador web predeterminado en la dirección correcta. Si no se abre automáticamente, visite manualmente http://localhost:4200/ en su navegador.
Confirmar ejecución del frontend: Debería cargarse la aplicación web de la biblioteca. En la interfaz podrá navegar por las funcionalidades: ver el listado de libros, consultar detalles, gestionar copias y registrar préstamos. Todas las operaciones del frontend harán peticiones al backend expuesto en el puerto 8080.
Acceder a la aplicación desde el navegador:
Con el backend corriendo en el puerto 8080 y el frontend corriendo en el puerto 4200, ya es posible utilizar el sistema. Abra un navegador web e ingrese a la URL: http://localhost:4200/.

Desde allí podrá interactuar con la aplicación de biblioteca mediante la interfaz web. Por ejemplo, podrá visualizar el catálogo de libros existentes, agregar nuevos libros, editar información, verificar cuántas copias hay disponibles y gestionar préstamos a estudiantes.
Asegúrese de que el servidor backend continúe ejecutándose; de lo contrario, la aplicación frontend no podrá obtener ni enviar datos (verá errores de conexión o CORS en la consola del navegador).

Notas y configuraciones adicionales

Puertos predeterminados: El backend usa el puerto 8080 de forma predeterminada. Si este puerto estuviera ocupado o desea cambiarlo, puede modificar el puerto configurando la propiedad server.port en application.properties o estableciendo la variable de entorno SERVER_PORT. El frontend Angular por defecto utiliza el puerto 4200 para el servidor de desarrollo; puede cambiarlo usando la opción --port en ng serve si fuera necesario.

Configuración de la base de datos: Los parámetros de conexión a Oracle (URL, usuario, contraseña) se encuentran en el archivo application.properties. Si la base de datos Oracle está en un host o instancia diferente, ajuste spring.datasource.url. Por ejemplo, para una instancia remota podría ser jdbc:oracle:thin:@HOST:PUERTO/NOMBRE_PDB. También puede manejar estas configuraciones mediante variables de entorno en lugar de editar el archivo, usando las propiedades estándar de Spring (por ejemplo, SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, etc.).

Migraciones de base de datos (Flyway): Flyway ejecutará automáticamente los scripts SQL de migración al iniciar el backend. Asegúrese de revisar la carpeta backend/src/main/resources/db/migration para conocer qué tablas y datos se crean en cada versión. Si necesita añadir nuevas tablas o campos, cree un nuevo script SQL siguiendo la convención de nombres de Flyway (V<numero>__descripcion.sql) en esa carpeta, de manera que se aplique en el arranque siguiente.
MyBatis Generator: El proyecto utiliza MyBatis Generator para generar los mappers y modelos basados en el esquema de base de datos. El archivo de configuración backend/generatorConfig.xml define cómo se genera el código a partir de las tablas. Si realiza cambios manuales en la base de datos (por ejemplo, añade tablas o columnas) y desea actualizar las clases Java correspondientes, puede ejecutar el plugin de MyBatis Generator mediante Maven:
./mvnw mybatis-generator:generate

Asegúrese de haber actualizado el esquema (migraciones Flyway) antes de regenerar las clases. Tenga en cuenta que cualquier cambio manual que haya hecho en las clases generadas podría ser sobrescrito por este proceso.

Bootstrap y estilos: El frontend utiliza Bootstrap para el diseño responsivo y estilos básicos. Asegúrese de que los archivos CSS/JS de Bootstrap estén correctamente incluidos (normalmente Angular los gestiona via Angular.json y angular.json ya referencia los estilos de Bootstrap instalados mediante npm). Esto debería estar listo tras la instalación de dependencias; si observa problemas de estilo, confirme que en angular.json esté importado el CSS de Bootstrap (por ejemplo, "node_modules/bootstrap/dist/css/bootstrap.min.css" en la sección de estilos).
