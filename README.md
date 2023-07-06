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

#### Enlace Video demostración
https://youtu.be/gMJWqQ19lcc

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
Página principal de la web donde se pordrán ver todos los eventos disponibles.
![NAV](/Capturas/Pagina_Principal.png)
Sign up de la web, donde el usuario se puede crear una cuenta.
![NAV](/Capturas/Sign_up.png)
Login de la web, donde una vez creada la cuenta, se inicia sesión desde aquí.
![NAV](/Capturas/Login.png)
Página principal después de loggearte, donde se muestran las otras páginas que como usuraio registrado puede entrar
![NAV](/Capturas/Pagina_Principal_logged.png)
Página de eventos donde se puede ver todos los eventos creados por el usurio, además de que también se puede crear un evento.
![NAV](/Capturas/My_events.png)
Página para crear evento, configurando las fechas, tamaño y nombre del torneo.
![NAV](/Capturas/Create_event.png)
Página de eventos con un evento ya creado.
![NAV](/Capturas/Event_Created.png)
Página para editar un evento ya creado.
![NAV](/Capturas/Edit_Event.png)
Página web principal de la web con un evento en marcha.
![NAV](/Capturas/Pagina_Principal_logged_evento.png)
Página del evento creado sin jugadores inscritos.
![NAV](/Capturas/Event.png)
Página del evento creado lleno y con los jugadores listos para apuntar los resultados.
![NAV](/Capturas/Event_ongoing.png)
Página de la cuenta del usuario para cambiar los datos de la cuenta.
![NAV](/Capturas/My_account.png)



## Fase III

### Diagramas

Se han actualizado los diagramas de entidad/relación y de navegación para esta fase.

#### Diagrama de navegación de la aplicación web
![NAV](/diagrams/diagram_nav_dad_updated.png)

#### Diagrama de clases de la aplicación
![NAV](/diagrams/Diagrama_Clases_Templates.png)

### Documentación interfaz del servicio interno
La implementación del servicio interno se ha utilizado el broker de mensajes RabbitMQ para enviar correos electronicos cuando el usuario crea una cuenta, recibe una notificación o modifica datos de su cuenta. 
Se ha creado una cola "messages" que será donde el servicio web mandará los mensajes codificados a través de una clase Publisher. Por otro lado el servicio interno tiene una clase Consumer que lee de la cola los mensajes, los descodifica, y envía los correos.

### Despliegue de la aplicación

#### Creación de los archivos jar
Para generar los archivos jar de la web y del servicio interno, se ha utilizado el mismo entorno de desarrollo Eclipse con la función: Proyect => Run As => Maven Install

#### Conexión y preparación de la máquina virtual 
Antes que nada, se ha creado una máquina virtual con VirtualBox usando Ubunto como sistema operativo, desde el cual se hara el despliegue de la página web. Una vez creado, se han generado las claves SSH privada y pública con el siguiente comando:
```
$sudo ssh-keygen -t rsa
```
Una vez obtenida la clave privada de la máquina virtual, procedemos a realizar la conexión a ella desde el equipo local. Para ello se utiliza el siguiente comando desde la PowerShell:
```
ssh -i nombre_archivo_clave_privada.pem ubuntu@ip
```
Para subir los archivos .jar a la máquina virtual se ha utilizado el siguiente comando en la PowerShell:
```
scp nombre_archivo_clave_privada.pen nombre_archivo_copiar ubuntu@ip:/directorio_destino
```
Dentro de la máquina virtual, se hace unos preparativos previos antes de iniciar la página web, que es instalar el JDK, la base de datos de mySQL y RabbitMQ:
JDK:
```
sudo apt install default-jdk
```
mySQL:
```
sudo apt install mysql-server
sudo mysql
	create database efightclub_schema;
	create user 'admin'@'%' indentified by 'WoozyGuy';
	grant all on efightclub_schema.* to 'admin'@'%';
 exit
 ```
RabbitMQ:
```
sudo apt-get install curl gnupg apt-transport-https -y
curl -1sLf "https://keys.openpgp.org/vks/v1/by-fingerprint/0A9AF2115F4687BD29803A206B73A36E6026DFCA" | sudo gpg --dearmor | sudo tee /usr/share/keyrings/com.rabbitmq.team.gpg > /dev/null
curl -1sLf https://ppa1.novemberain.com/gpg.E495BB49CC4BBE5B.key | sudo gpg --dearmor | sudo tee /usr/share/keyrings/rabbitmq.E495BB49CC4BBE5B.gpg > /dev/null
curl -1sLf https://ppa1.novemberain.com/gpg.9F4587F226208342.key | sudo gpg --dearmor | sudo tee /usr/share/keyrings/rabbitmq.9F4587F226208342.gpg > /dev/null
sudo tee /etc/apt/sources.list.d/rabbitmq.list <<EOF

	deb [signed-by=/usr/share/keyrings/rabbitmq.E495BB49CC4BBE5B.gpg] https://ppa1.novemberain.com/rabbitmq/rabbitmq-erlang/deb/ubuntu jammy main
	deb-src [signed-by=/usr/share/keyrings/rabbitmq.E495BB49CC4BBE5B.gpg] https://ppa1.novemberain.com/rabbitmq/rabbitmq-erlang/deb/ubuntu jammy main
	
	deb [signed-by=/usr/share/keyrings/rabbitmq.9F4587F226208342.gpg] https://ppa1.novemberain.com/rabbitmq/rabbitmq-server/deb/ubuntu jammy main
	deb-src [signed-by=/usr/share/keyrings/rabbitmq.9F4587F226208342.gpg] https://ppa1.novemberain.com/rabbitmq/rabbitmq-server/deb/ubuntu jammy main
	EOF

sudo apt-get update -y
sudo apt-get install -y erlang-base \
                        erlang-asn1 erlang-crypto erlang-eldap erlang-ftp erlang-inets \
                        erlang-mnesia erlang-os-mon erlang-parsetools erlang-public-key \
                        erlang-runtime-tools erlang-snmp erlang-ssl \
                        erlang-syntax-tools erlang-tftp erlang-tools erlang-xmerl
sudo apt-get install rabbitmq-server -y --fix-missing
```
Por último, para ejecutar ambos servicios, web e interno, se usa el siguiente comando:
```
java -jar nombre_archivo_java.jar
```
## Fase IV

### Diagramas

#### Diagrama de clases de la aplicación actualizados
![NAV](/diagrams/Diagrama_Clases_Templates_actualizado.png)
#### Diagrama de los contenedores e infraestructura en Docker
![NAV](/diagrams/diagram_docker_dad.png)


## Licencia
Este proyecto está bajo la licencia `Apache License 2.0`. Mira el archivo [LICENSE](LICENSE) para más detalles.
