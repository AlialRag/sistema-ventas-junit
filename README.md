# sistema-ventas-junit

Proyecto de ejemplo de la monografía sobre JUnit: sistema de ventas con pruebas
unitarias (JUnit Jupiter), pruebas con Mockito, cobertura con JaCoCo y CI con GitHub Actions.

## Requisitos
- JDK 17 o superior
- IntelliJ IDEA (Community o Ultimate) o Maven 3.8+

## Ejecutar
    mvn clean test      # compila y ejecuta las pruebas
    mvn clean verify    # además genera el reporte JaCoCo y exige 80 % de cobertura de líneas

Reporte de cobertura: `target/site/jacoco/index.html`

## Estructura
    src/main/java/com/empresa/ventas/   código de producción
    src/test/java/com/empresa/ventas/   pruebas (JUnit Jupiter + Mockito)
    .github/workflows/ci.yml            pipeline de integración continua

Versión de JUnit: se controla con la propiedad `junit.version` del pom.xml.
