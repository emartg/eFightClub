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

## Licencia
Este proyecto está bajo la licencia `Apache License 2.0`. Mira el archivo [LICENSE](LICENSE) para más detalles.
