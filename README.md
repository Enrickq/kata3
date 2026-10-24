# Kata 3 — Transformación de datos a JSON

## Objetivo

Aplicación desarrollada en Java 27 que transforma un dataset CSV de libros en objetos de dominio y genera un fichero JSON con la colección de registros válidos y un resumen calculado.

El ejercicio separa lectura, parsing, validación, transformación y serialización.

## Tecnologías

- Java 27
- Maven
- Gson 2.14.0
- JUnit Jupiter 5.11.4
- AssertJ 3.27.3

## Compilación y ejecución

Desde la raíz del proyecto:

`mvn clean test`

`mvn exec:java`

La entrada predeterminada es `src/main/resources/libros.csv`.

La salida se genera en `target/salida/libros.json`.

También se pueden indicar rutas:

`mvn exec:java -Dexec.args="mis-libros.csv target/salida/resultado.json"`

## Estructura

- `Libro`: entidad de dominio independiente de CSV y JSON.
- `ResultadoParseo`: contiene los libros válidos y el número de descartes.
- `CsvReader`: lectura a partir de un Reader.
- `CsvLibroParser`: interpretación y validación de registros.
- `DocumentoJson`: representación de la salida y sus metadatos.
- `JsonLibroWriter`: serialización con Gson.
- `Main`: coordinación del proceso y de los archivos.

## Formato de entrada

CSV sencillo con cabecera `titulo,autor,anio`.

Cada fila debe contener tres campos. No se admiten campos vacíos, años no numéricos ni años menores o iguales que cero.

Esta implementación no contempla comas internas entrecomilladas.

## Resultado esperado

El dataset final contiene cuatro libros válidos y dos registros descartados.

El bloque `resumen` incluye:

- `validos`: 4
- `descartados`: 2
- `totalProcesados`: 6

Los ejemplos están en `src/main/resources/libros.csv` y `src/test/resources/libros-esperados.json`.

## Verificación

Las pruebas JUnit y AssertJ comprueban los registros válidos e inválidos, la cabecera, la entrada vacía, la lectura desde diferentes orígenes y la equivalencia estructural del JSON generado con el esperado.

La salida generada se vuelve a interpretar con Gson para verificar que es JSON válido.

## Repetición deliberada

La kata se ha reconstruido tres veces manteniendo el mismo objetivo y las mismas responsabilidades principales.

- `kata3-r1`: primera reconstrucción con un dataset reducido.
- `kata3-r2`: segunda reconstrucción con un dataset ampliado y otro caso inválido.
- `kata3-r3`: tercera reconstrucción con el metadato adicional `totalProcesados`.

La rama `main` contiene la versión final y las etiquetas permiten consultar los estados anteriores.

## Vídeo explicativo

Pendiente de publicar.
