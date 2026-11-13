# Máquina de Café: Java y JUnit 4

Como consumidor de café, deseo tomar un vaso de café para mitigar el sueño.

Proyecto de dominio sin interfaz gráfica. Incluye las cuatro clases completas, los 19 casos solicitados y 8 pruebas adicionales. Las clases están en el paquete predeterminado para facilitar su uso con los nombres de la especificación.

## Requisitos y ejecución

- Con Maven: JDK 8 o posterior y `mvn test`.
- Sin Maven, en Windows: JDK 9 o posterior y PowerShell. Desde la carpeta del proyecto:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\test.ps1
```

El script descarga JUnit 4.13.2 y Hamcrest 1.3 desde Maven Central a `.deps/` en la primera ejecución; las siguientes usan los archivos locales. Compila para Java 8 y ejecuta las cuatro clases de prueba con `JUnitCore`. `ExecutionPolicy Bypass` solo se aplica al proceso iniciado por el comando.

## Código y relación con las pruebas

| Producción | Pruebas | Responsabilidad comprobada |
| --- | --- | --- |
| `src/main/java/Vaso.java` | `src/test/java/TestVaso.java` | Consultar disponibilidad y descontar vasos. |
| `src/main/java/Cafetera.java` | `src/test/java/TestCafetera.java` | Consultar disponibilidad y descontar onzas de café. |
| `src/main/java/Azucarero.java` | `src/test/java/TestAzuquero.java` | Consultar disponibilidad y descontar azúcar. |
| `src/main/java/MaquinaDeCafe.java` | `src/test/java/TestMaquinaDeCafe.java` | Seleccionar tamaño, validar el pedido y descontar los tres insumos. |

Se conserva el nombre `TestAzuquero` solicitado; la clase de producción se llama `Azucarero`.

Cada prueba prepara inventarios nuevos mediante `@Before`, ejecuta una operación y comprueba el resultado con aserciones de JUnit 4. Se usan existencias exactas para verificar que `has...` compara con `>=`. La selección se comprueba con `assertSame`: devuelve el mismo objeto de inventario configurado, conservando su estado.

## Reglas de servicio

- `pequeno`: 3 oz; `mediano`: 5 oz; `grande`: 7 oz. El contenido se establece al construir cada `Vaso` y puede modificarse con su setter.
- Café necesario = `vaso.getContenido() * cantidadVasos`.
- `cantidadAzucar` es el **total del pedido**. Por ejemplo, 3 vasos medianos con 4 unidades de azúcar consumen 3 vasos, 15 oz y 4 unidades de azúcar.
- Se permite pedir café sin azúcar (`cantidadAzucar = 0`).
- Se valida en orden: vasos, café, azúcar. Los mensajes exactos son `"No hay Vasos"`, `"No hay Cafe"`, `"No hay Azucar"` y `"Felicitaciones"`.
- Solo se descuenta inventario cuando las tres validaciones pasan. Un pedido rechazado no consume insumos.
- Inventarios negativos, contenido no positivo, pedidos sin vasos y azúcar negativa generan `IllegalArgumentException`. Un tipo de vaso desconocido también genera esa excepción.
- Una máquina recién creada tiene los tres tamaños configurados, con inventarios vacíos. Se abastece mediante setters o se le asignan objetos con inventario.

## Ejemplo

```java
MaquinaDeCafe maquina = new MaquinaDeCafe();
maquina.setCafetera(new Cafetera(100));
maquina.setAzucarero(new Azucarero(20));
maquina.setVasosPequeno(new Vaso(10, 3));
maquina.setVasosMediano(new Vaso(10, 5));
maquina.setVasosGrande(new Vaso(10, 7));

Vaso vaso = maquina.getTipoDeVaso("mediano");
String resultado = maquina.getVasoDeCafe(vaso, 2, 3);
// resultado: "Felicitaciones"
// Quedan 8 vasos medianos, 90 oz de cafe y 17 unidades de azucar.
```

## Ciclo TDD realizado

1. **Red:** se escribieron las pruebas y firmas de producción con implementaciones vacías. JUnit ejecutó 27 pruebas y reportó 24 fallos. Las tres consultas de insuficiencia ya pasaban porque los métodos provisionales devolvían `false`.
2. **Green:** se implementaron los inventarios, la selección y el servicio. Resultado observado: `OK (27 tests)`.
3. **Refactor:** los constructores de los inventarios reutilizan los setters para eliminar validaciones duplicadas. Se ejecutó nuevamente la misma suite: `OK (27 tests)`.

Las 8 pruebas adicionales cubren pedidos de varios vasos, café suficiente para todo el pedido, café sin azúcar, consumo exacto, prioridad de errores, tamaño desconocido y dos pedidos con cantidades inválidas. Las pruebas de errores de inventario también verifican que no haya descuentos parciales.

Para continuar practicando TDD, agrega una prueba que falle para cada nueva regla; implementa lo mínimo para hacerla pasar y refactoriza conservando la suite en verde.
