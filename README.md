# Vega-post1-u11

## Unidad 11 - Post Contenido 1

Proyecto Spring Boot en Java 17 para el laboratorio de refactorizacion avanzada y Clean Code profundo.

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

## Comandos requeridos

Compilar el proyecto:

```bash
mvn compile
```

Ejecutar pruebas:

```bash
mvn test
```

Analisis inicial con SonarQube:

```bash
mvn verify sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=TU_TOKEN \
  -Dsonar.projectKey=refactoring-u11
```

## Metricas SonarQube

| Metrica | Antes de refactorizar | Despues de refactorizar |
| --- | ---: | ---: |
| Complejidad ciclomatica de `procesarPedido` | Pendiente de analisis inicial | Pendiente |
| Code Smells totales | Pendiente de analisis inicial | Pendiente |
| Technical Debt Ratio | Pendiente de analisis inicial | Pendiente |

## Capturas

Guardar las evidencias solicitadas por la rubrica en la carpeta `docs/sonarqube`:

| Captura | Archivo esperado |
| --- | --- |
| Dashboard inicial | `docs/sonarqube/antes.png` |
| Dashboard final | `docs/sonarqube/despues.png` |
