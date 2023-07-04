# eFightClub 
Repositorio para la práctica de Desarrollo de Aplicaciones Distribuidas (DAD) de la URJC. 
 
## Integrantes                                                                                

Eric Martínez Gamero:   
- :adult: [emartg](https://github.com/emartg)   
- :envelope: e.martinezg.2019@alumnos.urjc.es

Edson Víctor Fernández Espinoza:

- :adult: [edsonfe92](https://github.com/edsonfe92)   
- :envelope: ev.fernandez.2019@alumnos.urjc.es

Juan de Carlos López:   
- :adult: [Scloon44](https://github.com/Scloon44)   
- :envelope: j.decarlos.2019@alumnos.urjc.es 
 
 
## :open_book: Tabla de contenidos
 
- [Fase I](#fase-I)
    - [Descripción de la aplicación web](#descripción-de-la-aplicación-web) 
    - [Funcionalidades](#funcionalidades) 
    - [Entidades](#entidades) 
    - [Funcionalidades del servicio interno](#funcionalidades-del-servicio-interno)
- [Fase II](#fase-II)
	- [Diagramas](#diagramas)
- [Licencia](#licencia) 
 
## Fase I 
 
### Descripción de la aplicación web 
`eFightClub` es una aplicación web que permite visualizar y participar en torneos de videojuegos de lucha *online*. 
 
### Funcionalidades 

- **Funcionalidades públicas**: El usuario no registrado podrá:
     
     - Visualizar los torneos públicos en la página y sus resultados.
     
     - Registrarse con un correo y nombre únicos y una contraseña.
 
- **Funcionalidades privadas**: Un usuario registrado podrá además: 

     - Crear un torneo. Implica elegir el *banner* y la imagen del evento, determinar el juego y definir su estructura.
     
     - Gestionar un torneo creado.
     
     - Confirmar el resultado de un partido en el que se esté participando.
     
     - Suscribirse a un torneo para recibir notificaciones.
     
     - Unirse a un torneo de un juego en concreto, lo que supone suscribirse a sus noticias de manera automática.

     - Ver y gestionar su perfil.

### Entidades 
 
- **Usuario (*User*)**: Representa a los usuarios del sistema, que podrán crear, unirse o suscribirse a torneos (eventos). 
 
- **Evento (*Event*)**: Representa a los torneos creados en el sistema. Estos torneos son de un juego concreto y tienen un esquema personalizable compuesto por partidas entre jugadores.
 
- **Partida (*Match*)**: Representa un enfrentamiento entre dos usuarios. Puede estar vacía si aún no se conoce a los contendientes.
 
- **Notificación (*Notification*)**: Representa una noticia sobre un torneo (evento) al cual un usuario registrado está suscrito para ser notificado de su progreso.

### Funcionalidades del servicio interno

- Envío de correo electrónico al usuario tras registrarse, al crear, unirse o suscribirse a un evento.

- Sistema de envío de notificaciones en base a lo sucedido en los eventos.

- Sistema de login y registro de usuarios.
 
- Sistema de aleatorización de los enfrentamientos de los eventos.

- Sistema de actualización de los *brackets* tras la finalización de una partida.

## Fase II

### Diagramas
#### Diagrama de navegación de la aplicación web
![NAV](/diagrams/diagram_nav_dad.png)

#### Diagrama de entidad/relación
![NAV](/diagrams/diagram_er_dad.png)

#### Diagrama de entidad/relación con atributos
![NAV](/diagrams/diagram_er_attr_dad.png)

### Capturas de pantalla


## Fase III

### Diagramas

Se han actualizado los diagramas de entidad/relación y de navegación para esta fase.

#### Diagrama de navegación de la aplicación web
![NAV](/diagrams/diagram_nav_dad.png)

#### Diagrama de clases de la aplicación

### Documentación interfaz del servicio interno
La implementación del servicio interno se ha utilizado el broker de mensajes RabbitMQ para enviar correos electronicos cuando el usuario crea una cuenta, recibe una notificación o modifica datos de su cuenta. 
Se ha creado una cola "messages" que será donde el servicio web mandará los mensajes codificados a través de una clase Publisher. Por otro lado el servicio interno tiene una clase Consumer que lee de la cola los mensajes, los descodifica, y envía los correos.

### Despliegue de la aplicación

#### Creación de los archivos jar
Para generar los archivos jar de la web y del servicio interno, se ha utilizado el mismo entorno de desarrollo Eclipse con la función: Proyect => Run As => Maven Install

#### Conexión y preparación de la máquina virtual 
Antes que nada, se ha creado una máquina virtual con VirtualBox usando Ubunto como sistema operativo, desde el cual se hara el despliegue de la página web. Una vez creado, se han generado las claves SSH privada y pública con el siguiente comando:

$sudo ssh-keygen -t rsa

Una vez obtenida la clave privada de la máquina virtual, procedemos a realizar la conexión a ella desde el equipo local. Para ello se utiliza el siguiente comando desde la PowerShell:

ssh -i nombre_archivo_clave_privada.pem ubuntu@ip


## Licencia
Este proyecto está bajo la licencia `Apache License 2.0`. Mira el archivo [LICENSE](LICENSE) para más detalles.
