# Arquitectura de UBLKit

La arquitectura de este proyecto se cimienta profundamente en el patrón de **Arquitectura Hexagonal**.

## Principio Principal
> El dominio nunca conoce frameworks, ni de XML, ni HTTP, ni Spring, ni Quarkus.

## Regla de Dependencia
Las dependencias fluyen siempre hacia el núcleo (el dominio/core).
- **Correcto:** Infraestructura -> Aplicación -> Dominio
- **Incorrecto:** Dominio -> Spring, Quarkus o XML

### Estructura base de módulos
El diseño divide un módulo con interfaces *puerto* (que dictan qué requiere el negocio) y sus *adaptadores* (ejemplo, cómo se emiten los correos, cómo se almacena, cómo se inyectan las credenciales HTTP).

## Decisiones Claves (ADR)
1. **Java puro primero:** Antes de acoplarnos a Spring o Quarkus, la funcionalidad debe funcionar limpiamente por interfaces Java.
2. **Validaciones por Resultado explicitadas:** Optamos por un patrón de notificación/resultado para las validaciones en lugar de estallar excepciones en el primer error. Esto permite al usuario ver todas las incidencias al mismo tiempo.
3. **No exponer XML:** La abstracción superior oculta la estructura UBL a un modelo con sentido de negocio, facilitando cambios de versiones del estándar UBL.
