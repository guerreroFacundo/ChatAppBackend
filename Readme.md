# ChatApp

ChatApp es una aplicación de mensajería en tiempo real que permite a los usuarios registrarse, iniciar sesión y comunicarse entre sí mediante mensajes de texto. La aplicación utiliza tecnologías modernas como Spring Boot para el backend y WebSocket para la comunicación en tiempo real.

## Características

- **Registro de usuarios**: Los nuevos usuarios pueden registrarse proporcionando un nombre de usuario, una contraseña y un correo electrónico.
- **Inicio de sesión**: Los usuarios pueden autenticarse utilizando su nombre de usuario y contraseña.
- **Envío de mensajes**: Los usuarios pueden enviar y recibir mensajes en tiempo real.
- **Historial de mensajes**: Los usuarios pueden recuperar el historial de mensajes enviados y recibidos.
- **Manejo de errores**: La aplicación cuenta con un manejo de excepciones para proporcionar respuestas adecuadas en caso de errores.

## Tecnologías utilizadas

- **Backend**: Spring Boot
- **Base de datos**: JPA/Hibernate con una base de datos relacional (ej. MySQL)
- **Seguridad**: Spring Security para la autenticación y autorización
- **Comunicación en tiempo real**: WebSocket
- **DTOs y Mappers**: Utiliza patrones de diseño para la transferencia de datos entre las capas de la aplicación.

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tu_usuario/ChatApp.git
   cd ChatApp
   ```
2. Asegúrate de tener Java y Maven instalados en tu máquina.

3. Configura la base de datos en el archivo `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```
4. Compila y ejecuta la aplicación:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
## Uso

- **Registro de usuario**: Realiza una solicitud POST a `/api/users/register` con los parámetros `username`, `password` y `email`.
- **Inicio de sesión**: Realiza una solicitud POST a `/api/users/login` con un JSON que contenga `username` y `password`.
- **Enviar mensaje**: Realiza una solicitud POST a `/api/messages/send` con los parámetros `senderId`, `receiverId`, `content` y opcionalmente `replyTo`.
- **Obtener mensajes**: Realiza una solicitud GET a `/api/messages/obtenerMensajes` con los parámetros `userId` y `contactId`.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y haz commit (`git commit -m 'Agrega una nueva característica'`).
4. Haz push a la rama (`git push origin feature/nueva-caracteristica`).
5. Abre un Pull Request.


Siente libre de personalizar cualquier sección según las necesidades específicas de tu aplicación.
