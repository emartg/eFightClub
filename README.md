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
- [Licencia](#licencia) 
 
## Fase I 
 
### Descripción de la aplicación web 
`eFightClub` es una aplicación web que permite visualizar y participar en torneos de videojuegos de lucha online. 
 
### Funcionalidades 
``` 
- **Funcionalidades públicas**: El usuario no registrado puede visualizar los torneos públicos en la página, y sus resultados. 
 
- **Funcionalidades privadas**: Un usuario registrado podrá inscribirse y participar en torneos, al participar en un partido, cada usuario debe confirmar que se encuentra preparado antes de comenzar. 
Además, permite al usuario crear y personalizar sus propios torneos, con normas y el diagrama del torneo. 
``` 
### Entidades 
``` 
- **Administrador**: Representa a los administradores del sistema. Realizarán la gestión tanto de los torneos como de las cuentas del sistema. 
 
- **Usuario**: Representa a los usuarios del sistema que podrán crear, unirse o suscribirse a torneos. 
 
- **Torneo**: Representa a los torneos creados en el sistema. Estos torneos son de un juego seleccionable y tienen un esquema personalizable compuesto por partidas entre jugadores. 
 
- **Juego**: Representa el juego que se está jugando en un torneo en específico. El juego de un torneo se puede seleccionar en la creación entre una serie de predefinidos. 
 
- **Partida**: Representa un enfrentamiento entre dos usuarios. Puede estar vacía si aún no se conoce a los contendientes. 
 
- **Noticia**: Representa a las noticias de los torneos a los cuales un usuario registrado está suscrito para ser notificado de su progreso. 
``` 
### Funcionalidades del servicio interno 
``` 
- Envío de correo electrónico al usuario tras registrarse, al crear, unirse o suscribirse a un torneo. 
 
- Unirse a un torneo de un juego en concreto. 

- Creación y gestión de un torneo. 

- Suscripción a un torneo. 

- Sistema de confirmación para iniciar y para finalizar un partido entre los contendientes. 

 
- Gestión de un perfil de usuario. 
``` 

## Licencia
Este proyecto está bajo la licencia `Apache License 2.0`. Mira el archivo [LICENSE](LICENSE) para más detalles.
