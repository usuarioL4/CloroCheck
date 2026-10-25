# Control de Cloro

Aplicación Android desarrollada en Kotlin para registrar y consultar mediciones de niveles de cloro, identificar valores fuera del rango establecido y gestionar alertas.

## Funciones principales

- **Registro de mediciones:** guarda la granja, el punto de medición y el nivel de cloro.
- **Clasificación automática:** identifica niveles normales, advertencias y críticos.
- **Sistema de alertas:** genera alertas cuando una medición está fuera del rango establecido.
- **Acciones correctivas:** permite registrar las acciones realizadas para atender una alerta.
- **Cierre de alertas:** permite marcar alertas como atendidas sin eliminarlas.
- **Historial:** permite consultar mediciones anteriores y filtrarlas por granja, punto y estado.
- **Resumen estadístico:** muestra el total de mediciones, las alertas abiertas, el promedio de cloro y la clasificación de los niveles.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Room Database
- Kotlin Coroutines y Flow

## Almacenamiento

La aplicación utiliza una base de datos local Room para guardar las mediciones y las alertas. Actualmente, trabaja con datos locales y no requiere una API externa.

## Cómo ejecutar el proyecto

1. Abrir el proyecto en Android Studio.
2. Esperar a que finalice la sincronización de Gradle.
3. Ejecutar la aplicación en un emulador o dispositivo Android.

## Nota

Los rangos de cloro utilizados para clasificar las mediciones están definidos en el código y deben validarse según los requisitos del proyecto y la normativa aplicable antes de su uso real.

## Estado del proyecto

Aplicación en desarrollo, con funciones de registro, historial, resumen estadístico y gestión de alertas.
