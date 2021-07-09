# Microservicio Nodo-Bloque
Este es el microservicio nodo-bloque.

## Instrucciones
Para ejecutar este microservicio en **IntelliJ** realizar lo siguiente.
_Abrir el archivo pom ubicado en:_

```
java-project-blockchain/backend/microservice-node-block/node-block/pom.xml
```
_La base de datos utilizada es **MongoDB (atlas)**. Para su uso, configurar el archivo **aplication.properties** ubicado en:_
```
java-project-blockchain/backend/microservice-node-block/node-block/src/main/resources/application.properties
```
_Levantar previamente el servicio en IntelliJ con:_
```
Mayus+F10 o Ejecutar manualmente
```

## RestClient
_La mejor manera de probar la API es ejecutar los metodos de la clase RestClient ubicada en:_

```
java-project-blockchain/backend/microservice-node-block/node-block/src/main/java/com/app/blockchain/RestClient.java
```

## EndPoints
Estos son todos los endpoints de la API.

_Obtener la cadena de bloques:_

```
http://localhost:8093/blocks
```

_Obtener el ultimo bloque de la cadena:_

```
http://localhost:8093/lastblock
```

_Crear un bloque y reestransmitirlo:_

```
http://localhost:8093/createblock?send=true
```

_Guardar un bloque proveniente de otro nodo:_

```
http://localhost:8093/lastblock
```

_Guardar una cadena bloques proveniente de otro nodo:_

```
http://localhost:8093/saveblockchain
```

_Obtener todas las URL´s de los nodos en la red:_

```
http://localhost:8093/urlnodes
```

_Agregar un nodo a la red:_

```
http://localhost:8093/addnode
```

