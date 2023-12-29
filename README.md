# Practica para capacitacion de Sofka
### API REST HOME BANKING

 ![Static Badge](https://img.shields.io/badge/0.0.1-version-%2300bab4) ![Static Badge](https://img.shields.io/badge/0.0.1-release-%2300bab4)

## Crear un API REST que tenga las siguientes caracteristicas:

### Objetivo:

* Refactorizar el proyecto construido hasta el momento e implementar un segundo servicio capaz de
persistir en una base de datos una lista de eventos consumidos mediante colas de RabbitMQ.
Consideraciones:

* Se debe realizar el procesado de peticiones en los endpoints a través de RouterFucntions.

* Debe haber una migración en términos de concepto y código de Servicios a Casos de Uso,
donde cada acción del dominio sea representada por su respectivo caso de uso.

* Las pruebas unitarias deben ser re-factorizadas en orden de cumplir con las
consideraciones anteriores y estas deben evaluar el buen funcionamiento de controladores
y casos de uso, con sus respectivos test de casos de fallo y éxito según corresponda.

* Todas las imágenes deben estar publicadas y consumidas desde Docker Hub.

* Los proyectos deben estar separados cada uno en su respectivo repositorio en GitHub con
su correcto control de versiones y nombrado de ramas y commits.

### Requerimientos funcionales:

* Mostrar evidencia de la construcción de las imágenes con sus respectivos Dockerfile.

* Ejecutar los servicios mediante un archivo docker compose, mostrando el correcto
funcionamiento de los endpoints y las funcionalidades requeridas en el reto inicial (Manejo
de cuenta bancaria).

* Ejecutar los servicios por medio de manifiestos de Kubernetes, las variables de entorno y
configuraciones en general deben ser realizadas mediante Secrets.

* El microservicio de trazabilidad debe haber un mensaje al ingresar la petición, evidencia de
todos los procesos realizados, como almacenamiento en bases de datos y publicación en
colas de mensajería, y un mensaje con finalización con éxito o error según corresponda.

* En el proyecto de manejo de cuenta bancaria se debe dejar uno de los depósitos forzando
un error, con afán de verificar el correcto manejo de los mismos.

# Configuraciones env

```
export PORTAPP=8080;

export DEPOSITO=2.00;
export CUENTA=1.50;
export SUCURSAL=0.00;

export RABBITURL=amqp://guest:guest@localhost:5672;

export HOSTDB=localhost;
export PORTDB=27017;

```
