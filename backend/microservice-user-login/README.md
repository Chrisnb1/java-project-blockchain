# Microservicio Usuarios
Este es el microservicio User.

## Intrucciones 
Para ejecutar este microservicio en **IntelliJ** realizar lo siguiente.
_Abrir el archivo pom ubicado en:_

```
java-project-blockchain/backend/microservice-user-login/user/pom.xml
```
_La base de datos utilizada es **MongoDB (atlas)**. Para su uso, configurar el archivo **aplication.properties** ubicado en:_
```
java-project-blockchain/backend/microservice-user-login/user/src/main/resources/application.properties
```
_Levantar previamente el servicio en IntelliJ con:_
```
Mayus+F10 o Ejecutar manualmente
```

# EndPoints
Estos son todos los endpoints de la API.
_Obtener la lista de usuarios [GET] :_
```
http://localhost:8094/users
```

_Registrar un usuario [POST] :_
```
http://localhost:8094/registration
```

_Validar un usuario [GET]:_
```
http://localhost:8094/login
```
