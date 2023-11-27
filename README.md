# spring-web-secure

## Especificaciones

- **Acceso Público:**
    - Todo el mundo tiene acceso al índice de la aplicación, incluso sin iniciar sesión.
    - Todo el mundo tiene acceso a `/hola`, incluso sin iniciar sesión.

- **Acceso Restringido a listarUsuarios:**
    - Es necesario iniciar sesión con un usuario de tipo USER o ADMIN.

- **Privilegios del ADMIN:**
    - El usuario con el rol de ADMIN puede acceder a todas las páginas del usuario (USER).
    - El ADMIN tiene permisos para realizar todas las operaciones en la aplicación.

- **Privilegios del USER:**
    - El usuario con el rol de USER tiene acceso limitado:
        - Puede editar el nombre o el correo electrónico del usuario.
        - No tiene permisos para crear nuevos usuarios ni borrarlos.
