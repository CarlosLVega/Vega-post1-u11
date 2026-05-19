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
