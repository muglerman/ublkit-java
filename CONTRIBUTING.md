# Contribuir a UBLKit

¡Gracias por tu interés en mejorar UBLKit! Este es un proyecto comunitario y cualquier ayuda es bienvenida.

## Cómo empezar
1. Haz un **fork** del repositorio.
2. Crea una rama para tu feature o solución de error: `git checkout -b feature/mi-nueva-caracteristica`.
3. Haz tus cambios y asegúrate de mantener consistencia de naming, estilo y arquitectura del proyecto.
4. Añade tests unitarios para tus cambios.
5. Envía un **Pull Request**.

## Estándar de Código
- Usamos Java 21+.
- La **Arquitectura Hexagonal** es obligatoria: no añadas dependencias de frameworks al núcleo.
- Los modelos deben ser inmutables cuando sea posible (`record` o final con builder).
- Los nombres de clases y métodos deben ser descriptivos y en español para el dominio (ej. `BorradorFactura` vs `InvoiceDraft`).

## Mensajes de Commit
Sugerimos usar el estándar de **Conventional Commits**:
- `feat:` para nuevas funcionalidades.
- `fix:` para solución de errores.
- `docs:` para cambios en documentación.
- `test:` para añadir o mejorar tests.

## Proceso de Revisión
Todos los PRs requieren al menos una aprobación y deben pasar el build de Maven (`mvn compile`).
