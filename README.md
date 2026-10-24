# Kata 3 — Transformación de datos a JSON

## Objetivo
Convertir un CSV sencillo de libros en objetos de dominio, descartar registros inválidos y generar un fichero JSON con la colección y un resumen calculado. No requiere conexión a internet durante la ejecución.

## Requisitos y dependencias
- JDK 27
- Apache Maven 3.9 o compatible
- Gson 2.14.0 para serializar JSON
- JUnit Jupiter 5.11.4 y AssertJ 3.27.3 para pruebas

## Compilar, probar y ejecutar
Desde la raíz del repositorio:

```bash
mvn clean test
mvn exec:java
```

Entrada predeterminada: `src/main/resources/libros.csv`.
Salida predeterminada: `target/salida/libros.json` (se genera al ejecutar; no se versiona).
También puedes indicar rutas propias:

```bash
mvn exec:java -Dexec.args="mis-libros.csv target/salida/resultado.json"
```

**Formato admitido:** CSV sencillo con cabecera exacta `titulo,autor,anio` y tres campos por fila, sin comas internas entrecomilladas. Se descartan campos vacíos, filas con distinto número de columnas, años no numéricos y años menores o iguales que cero. Un fichero vacío genera una lista y un resumen vacíos. Una cabecera incorrecta provoca un error explícito.

## Estructura y responsabilidades
- `domain/model/Libro`: entidad del dominio, independiente del formato de origen.
- `domain/model/ResultadoParseo`: resultado de la transformación y número de descartes.
- `adapters/CsvReader`: lectura desde cualquier `Reader`; no decide qué filas son válidas.
- `adapters/CsvLibroParser`: comprueba cabecera, valida cada fila y crea objetos `Libro`.
- `adapters/DocumentoJson`: estructura JSON de salida y metadatos.
- `adapters/JsonLibroWriter`: serializa con Gson objetos ya validados, sin volver a analizarlos.
- `Main`: coordina las dependencias y administra los ficheros.

## Ejemplo y verificación
Entrada de ejemplo: `src/main/resources/libros.csv`.
Salida JSON esperada: `src/test/resources/libros-esperados.json`.
El programa produce cuatro libros válidos y descarta dos filas. El resumen incluye `validos`, `descartados` y `totalProcesados`.

Las pruebas de `mvn clean test` verifican: filas válidas e inválidas, cabecera errónea, dataset vacío, equivalencia de lectura desde memoria/recurso y comparación estructural del JSON generado con el JSON esperado, que se vuelve a parsear mediante Gson.

## Repetición deliberada
El mismo ejercicio se reconstruyó tres veces manteniendo lector, parser, transformación y serializador:
1. `kata3-r1`: primera ejecución con dataset reducido.
2. `kata3-r2`: reconstrucción con un dataset ligeramente ampliado y más filas inválidas.
3. `kata3-r3`: reconstrucción final que añade el metadato `totalProcesados`.

La rama principal `main` contiene la tercera versión, evaluable. Las versiones anteriores pueden consultarse en sus tags.

## Vídeo explicativo
**Pendiente de enlazar tras subir el vídeo a YouTube.**
