# Practica TDD: una prueba a la vez

Esta es una copia de trabajo independiente para reconstruir la maquina de cafe.
El proyecto terminado permanece en la carpeta superior como referencia.
Aqui solo existe la primera prueba de Azucarero y una implementacion provisional
que compila y hace fallar esa prueba. No hay mas pruebas activas ni soluciones
de las otras clases. La guia del aula virtual manda sobre el orden sugerido aqui.

## Abrir y ejecutar

En VS Code, usa **Archivo > Abrir area de trabajo desde archivo...** y selecciona
`practica-tdd.code-workspace`, que esta en la carpeta superior. Ese espacio de
trabajo abre solamente esta copia y permite importar su propio `pom.xml`.
Tambien puedes usar **Archivo > Abrir carpeta...** y seleccionar `practica-tdd`.
Abrir solamente el archivo Java mientras sigue abierta la carpeta original no
cambia el proyecto al que VS Code intenta asociarlo.

Si aparece el aviso `is not on the classpath of project maquina-de-cafe`, abre
primero el espacio de trabajo de practica y espera a que Java termine de
importar el proyecto. Si persiste, abre la paleta con `Ctrl+Shift+P` y ejecuta
**Java: Clean Java Language Server Workspace**, confirma el reinicio y espera
a que termine la importacion. En JAVA PROJECTS debe aparecer
`maquina-de-cafe-practica-tdd`.

Desde la terminal del proyecto original:

```powershell
cd .\practica-tdd
powershell -NoProfile -ExecutionPolicy Bypass -File .\test.ps1
```

Si ya abriste esta carpeta, ejecuta solo la segunda linea. Necesitas un JDK 9 o
posterior para el script (se encontro Java 21 al prepararlo). No necesitas Maven.
Las dependencias se reutilizan de la cache local o se descargan si faltan.
El comando funciona desde cualquier directorio si indicas la ruta del script.

El script compila las fuentes y ejecuta todas las clases `Test*.java` que hayas
creado. Guarda un registro por ejecucion en `evidencias/`. Un codigo de salida 1
por una asercion fallida es el rojo esperado; un error de compilacion se debe
resolver antes de comprobar el comportamiento. No ejecuta las pruebas del
proyecto terminado que esta fuera de esta carpeta.

Para ejecutar solo una clase cuando tengas varias:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\test.ps1 -Clase TestAzuquero
```

Tras cada cambio que pase la nueva prueba, ejecuta el comando sin `-Clase` para
comprobar tambien todas las pruebas anteriores. Si mas adelante instalas Maven,
puedes usar `mvn test` desde esta carpeta.

## Primer ciclo: azucar suficiente

1. Abre `src/test/java/TestAzuquero.java`. La unica prueba pide 10 unidades de
   azucar a un azucarero inicializado con 10 y espera `true`.
2. Ejecuta `test.ps1`. Debe mostrar `Tests run: 1, Failures: 1` porque
   `hasAzucar(10)` devuelve `false`. Toma la captura del rojo antes de modificar
   la implementacion.
3. En `src/main/java/Azucarero.java`, cambia solamente el cuerpo de `hasAzucar`:

   ```java
   public boolean hasAzucar(int cantidad) {
       return true;
   }
   ```

   Esta es una solucion provisional minima para la unica prueba existente.
4. Ejecuta de nuevo: debe mostrar `OK (1 test)`. Toma la captura del verde.
5. Revisa si hay duplicacion o nombres que mejorar. Por ahora no hace falta
   refactorizar. No es obligatorio cambiar codigo en cada ciclo.

## Segundo ciclo: azucar insuficiente

Solo despues de terminar el primer ciclo, agrega este metodo a `TestAzuquero`
y el import `import static org.junit.Assert.assertFalse;`:

```java
@Test
public void deberiaDevolverFalsoPorqueNoHaySuficienteAzucarEnElAzuquero() {
    Azucarero azucarero = new Azucarero(10);

    assertFalse(azucarero.hasAzucar(11));
}
```

Ejecuta ambas pruebas. Ahora falla la nueva, porque el metodo devuelve `true`
para cualquier cantidad. Toma la captura antes de corregirlo.

Para pasarla, guarda la cantidad inicial en un atributo del azucarero y haz que
`hasAzucar` compare la cantidad disponible con la solicitada usando `>=`.
Ejecuta de nuevo: deben pasar las dos pruebas. Toma la captura del verde.

Ahora ambas pruebas repiten la creacion de `new Azucarero(10)`: puedes extraerla
a un campo inicializado por un metodo con `@Before`. Ese cambio es la
refactorizacion; vuelve a ejecutar las dos pruebas y conserva el resultado.

## Continuar una prueba a la vez

La siguiente lista corresponde a los 19 casos base que aparecen en el proyecto
terminado. Es un orden sugerido empezando por el archivo que tenias abierto;
contrasta los casos y datos con la guia completa del aula virtual.
Las ocho pruebas adicionales del proyecto terminado pueden quedar para despues
de completar los casos exigidos.

| Paso | Clase | Prueba |
| --- | --- | --- |
| 01 | TestAzuquero | deberiaDevolverVerdaderoSiHaySuficienteAzucarEnElAzuquero |
| 02 | TestAzuquero | deberiaDevolverFalsoPorqueNoHaySuficienteAzucarEnElAzuquero |
| 03 | TestAzuquero | deberiaRestarAzucarAlAzuquero |
| 04 | TestCafetera | deberiaDevolverVerdaderoSiExisteCafe |
| 05 | TestCafetera | deberiaDevolverFalsoSiNoExisteCafe |
| 06 | TestCafetera | deberiaRestarcafeAlaCafetera |
| 07 | TestVaso | deberiaDevolverVerdaderoSiExistenVasos |
| 08 | TestVaso | deberiaDevolverFalsoSiNoExistenvasos |
| 09 | TestVaso | deberiaRestarCantidadDeVaso |
| 10 | TestMaquinaDeCafe | deberiaDevolverUnVasoPequeno |
| 11 | TestMaquinaDeCafe | deberiaDevolverUnVasoMediano |
| 12 | TestMaquinaDeCafe | deberiaDevolverUnVasoGrande |
| 13 | TestMaquinaDeCafe | deberiaDevolverNoHayVasos |
| 14 | TestMaquinaDeCafe | deberiaDevolverNoHayCafe |
| 15 | TestMaquinaDeCafe | deberiaDevolverNoHayAzucar |
| 16 | TestMaquinaDeCafe | deberiaRestarCafe |
| 17 | TestMaquinaDeCafe | deberiaRestarVaso |
| 18 | TestMaquinaDeCafe | deberiaRestarAzucar |
| 19 | TestMaquinaDeCafe | deberiaDevolverFelicitaciones |

Para cada paso, conserva todas las pruebas anteriores y agrega solamente la
siguiente. Puedes leer el caso correspondiente en el proyecto terminado, pero
no copies la clase completa de pruebas ni la implementacion completa.

Al agregar un metodo nuevo, crea primero la firma y una implementacion
provisional para que compile. Por ejemplo, para descontar azucar puedes agregar
`getCantidadDeAzucar()` devolviendo el inventario y `giveAzucar(int cantidad)`
con el cuerpo vacio. La nueva prueba debe detectar que no se descuenta.
Despues implementa el descuento y revisa si hay algo que refactorizar.

Ejecuta, observa y captura cada resultado antes de avanzar. Si una nueva prueba
ya pasa por lo implementado en un ciclo anterior, registra el hecho y continua;
no provoques fallos artificiales. Las capturas de esta reconstruccion muestran
este proceso nuevo, no el desarrollo previo del proyecto terminado.
