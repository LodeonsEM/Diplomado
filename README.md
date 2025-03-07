# Estrategia de Ramas (Desarrollo basado en troncos)

Para gestionar el código del proyecto, seguimos una estrategia "Desarrollo basado en troncos", optimizada para equipos pequeño. Esto permite una integración continua con menos fricción.

# Ramas principales
- 'main' → Contiene el código estable y en producción.
- 'dev' → Recibe todas las nuevas funcionalidades antes de pasar a `main`.

# Ramas de desarrollo
- 'feature/*' → Para nuevas funcionalidades.
- 'fix/*' → Para corregir errores.
- `release/*` → Para estabilizar versiones antes del despliegue.

# Flujo de trabajo
- Todo el desarrollo ocurre en main o dev.
- Se crean ramas cortas (feature/*, fix/*).
- Se realizan commits pequeños y pruebas continuas.
- Se fusionan los cambios en main rápidamente.
- Se eliminan las ramas temporales para mantener el repositorio limpio.
- Se usa CI/CD para pruebas y despliegues rápidos.
