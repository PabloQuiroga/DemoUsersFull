# Auth Android Project

Este proyecto es una implementación de un sistema de autenticación para Android, desarrollado siguiendo los principios de **Clean Architecture** y las mejores prácticas de desarrollo moderno con **Jetpack Compose**.

## 🚀 Arquitectura: Clean Architecture

El proyecto está dividido en capas bien definidas para garantizar el desacoplamiento, la testeabilidad y la escalabilidad:

### 1. Capa de Dominio (Domain)
Es la capa más interna y pura del proyecto. No tiene dependencias de librerías de Android o externas.
- **Entities**: Modelos de datos de negocio (`User.kt`).
- **Repositories (Interfaces)**: Definen el contrato de datos (`AuthRepository.kt`).
- **Use Cases**: Lógica de negocio específica (`LoginUseCase.kt`).

### 2. Capa de Datos (Data)
Implementa las interfaces definidas en la capa de dominio.
- **Repository Implementation**: Gestiona el flujo de datos (`AuthRepositoryImpl.kt`). Actualmente simula una API con retardos controlados.

### 3. Capa de Presentación (Presentation/UI)
Capa encargada de la interfaz de usuario y la lógica de vista, utilizando el patrón **MVVM** (Model-View-ViewModel).
- **UI (Jetpack Compose)**: Pantallas declarativas y reactivas (`LoginScreen.kt`).
- **ViewModels**: Gestionan el estado de la UI y se comunican con los casos de uso (`LoginViewModel.kt`).
- **State**: Manejo de estados de UI (`LoginState.kt`).

### 4. Capa de Infraestructura (DI)
- **Dependency Injection**: Implementada con **Hilt** para gestionar el ciclo de vida de las dependencias de forma automática.

## 🛠 Librerías Utilizadas

- **Jetpack Compose**: Para la construcción de interfaces de usuario modernas y declarativas.
- **Hilt (Dagger)**: Inyección de dependencias para Android.
- **KSP (Kotlin Symbol Processing)**: Procesamiento de anotaciones de alto rendimiento.
- **Kotlin Coroutines & Flow**: Manejo de programación asíncrona y reactiva.
- **Lifecycle ViewModel**: Para gestionar datos relacionados con la UI de manera consciente del ciclo de vida.
- **Navigation Compose**: Para la navegación entre pantallas (preparado).

## ⚙️ Configuración del Proyecto

- **Versión de Java**: 11 (JVM Target consistente en todo el proyecto).
- **Android Gradle Plugin (AGP)**: 8.7.2.
- **Kotlin**: 2.0.21.
- **Build System**: Gradle con Kotlin DSL (.kts).

## 📝 Cómo empezar

1. Clonar el repositorio.
2. Sincronizar el proyecto con Gradle.
3. Ejecutar en un emulador o dispositivo físico.

---
Desarrollado con ❤️ siguiendo los estándares de la comunidad Android.
