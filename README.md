# Vega-post1-u11

## Unidad 11 - Post Contenido 1

Proyecto Spring Boot en **Java 17** para el laboratorio de refactorizacion avanzada y Clean Code profundo.

| Dato | Valor |
| --- | --- |
| Lenguaje principal | Java |
| Framework | Spring Boot |
| Build tool | Maven |
| Dependencias requeridas | Spring Web, Spring Data JPA, H2 Database |
| Analisis de calidad | SonarQube Community |

> Este repositorio esta preparado para quedar como entrega Java: el codigo de aplicacion y pruebas esta en `src/main/java` y `src/test/java`.

## Estado inicial

Este primer estado del proyecto contiene el codigo intencionalmente deficiente solicitado en el PDF. La clase principal del laboratorio es `PedidoService`, que conserva los siguientes code smells:

| Code smell | Evidencia en el codigo inicial | Tecnica que se aplicara despues |
| --- | --- | --- |
| Large Class | `PedidoService` valida cliente, calcula totales, aplica descuentos, notifica y persiste. | Extract Class |
| Long Method | `procesarPedido` concentra varias responsabilidades en un solo metodo. | Extract Method |
| Primitive Obsession | Los datos del cliente se reciben como parametros primitivos y cadenas sueltas. | Value Object |
| Field Injection | `PedidoRepository` se inyecta con `@Autowired` sobre el campo. | Inyeccion por constructor |

## Refactorizacion aplicada

El segundo estado del proyecto elimina los smells principales con las tecnicas exigidas en la actividad:

| Tecnica | Cambio aplicado | Resultado |
| --- | --- | --- |
| Value Object | Se crearon `DatosCliente`, `Direccion`, `LineaPedido` y `CodigoDescuento`. | Los datos del cliente, direccion, lineas y descuentos dejaron de viajar como cadenas o listas primitivas sueltas. |
| Extract Method | `procesarPedido` delega en `metodoPagoInvalido`, `calcularTotal`, `calcularSubtotal`, `aplicarDescuento` y `persistirPedido`. | El metodo principal queda corto, legible y con complejidad ciclomatica baja. |
| Extract Class | La responsabilidad de notificacion se movio a `NotificacionService`. | `PedidoService` queda enfocado en orquestar el procesamiento del pedido. |
| Constructor Injection | `PedidoRepository` y `NotificacionService` se reciben por constructor. | Se elimina la inyeccion por campo y mejora la testabilidad. |

### Verificaciones de la rubrica

| Requisito | Estado |
| --- | --- |
| `DatosCliente` inmutable, con campos `final` y sin setters | Cumplido |
| `Direccion` inmutable, con campos `final` y sin setters | Cumplido |
| `procesarPedido` con maximo 8 lineas despues de refactorizar | Cumplido |
| `NotificacionService` independiente | Cumplido |
| Dependencias inyectadas por constructor | Cumplido |
| Pruebas automatizadas actualizadas | Cumplido |

## Estructura del proyecto

```text
.
|-- README.md
|-- pom.xml
|-- docs/
|   `-- sonarqube/
|       `-- README.md
|-- src/
|   |-- main/
|   |   |-- java/com/vega/post1u11/
|   |   |   |-- Post1U11Application.java
|   |   |   |-- model/
|   |   |   |-- repository/
|   |   |   `-- service/
|   |   `-- resources/application.properties
|   `-- test/java/com/vega/post1u11/service/PedidoServiceTest.java
```

## Comandos requeridos

Compilar el proyecto:

```bash
mvn compile
```

Ejecutar pruebas:

```bash
mvn test
```

Ejecutar verificacion completa de Maven:

```bash
mvn verify
```

Levantar SonarQube local con Docker:

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
```

Analisis inicial o final con SonarQube:

```bash
mvn verify sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=TU_TOKEN \
  -Dsonar.projectKey=refactoring-u11
```

## Metricas SonarQube

| Metrica | Antes de refactorizar | Despues de refactorizar |
| --- | ---: | ---: |
| Complejidad ciclomatica de `procesarPedido` | Completar con SonarQube inicial | Completar con SonarQube final |
| Code Smells totales | Completar con SonarQube inicial | Completar con SonarQube final |
| Technical Debt Ratio | Completar con SonarQube inicial | Completar con SonarQube final |

### Comparacion esperada

| Aspecto | Antes | Despues |
| --- | --- | --- |
| Parametros primitivos del cliente | Varias cadenas separadas en `procesarPedido` | `DatosCliente` y `Direccion` |
| Metodo principal | Mezcla validacion, calculo, descuento, notificacion y persistencia | Orquesta pasos pequenos |
| Notificacion | Dentro de `PedidoService` | `NotificacionService` independiente |
| Inyeccion de dependencias | Campo con `@Autowired` | Constructor |

## Capturas

Guardar las evidencias solicitadas por la rubrica en la carpeta `docs/sonarqube`:

| Captura | Archivo esperado |
| --- | --- |
| Dashboard inicial | `docs/sonarqube/antes.png` |
| Dashboard final | `docs/sonarqube/despues.png` |

## Commits sugeridos

La rubrica pide minimo 3 commits descriptivos:

| Commit | Contenido |
| --- | --- |
| `commit inicial codigo original` | Proyecto base con smells deliberados. |
| `refactorizacion clean code pedido service` | Value Objects, Extract Method, Extract Class e inyeccion por constructor. |
| `analisis final sonarqube documentacion` | README final, metricas reales y capturas de SonarQube. |
