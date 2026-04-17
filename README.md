# ClimaHoy (Clima Hoy)

App Android para **consultar el clima actual** de una ciudad/municipio o desde tu **ubicación actual**, con una UX pensada para búsquedas rápidas y guardado de consultas.

---

## Qué es / objetivo

**ClimaHoy** es una aplicación móvil (Android, Java) que permite:
- Buscar una **ciudad o municipio** y consultar el **clima actual**.
- Usar la **ubicación del dispositivo** para mostrar el clima donde estás.
- Mantener un registro de **últimas consultas** (historial) para recuperar información reciente.
- Incluir **ajustes de apariencia** (ej. modo oscuro).
- Integrar autenticación y persistencia en la nube (Firebase) para funcionalidades de usuario (según configuración del proyecto).

---

## Valor del proyecto (para reclutador)

Este repo demuestra trabajo real de un proyecto Android con:
- **Consumo de APIs REST** usando **Retrofit + Gson** (capas `network/*Service` y modelos `models/*`).
- **Arquitectura MVVM** (uso de `ViewModel` + `LiveData`).
- **Persistencia local** con **Room** (base de datos local para consultas).
- **Geolocalización** con Google Play Services (Fused Location Provider).
- **Firebase Auth + Firebase Realtime Database** (autenticación + almacenamiento en nube).
- **Pantallas/UX**: Autocomplete de ciudades, búsqueda, menús y pantalla de “acerca de”.

---

## Funcionalidades principales

- **Búsqueda por texto**: el usuario escribe ciudad/municipio y recibe sugerencias.
- **Consulta meteorológica**: obtiene el clima actual (temperatura/estado, etc. según el endpoint).
- **Ubicación actual**: permite consultar el clima usando permisos de localización.
- **Historial / últimas consultas**: guarda las consultas recientes (Room + ViewModel).
- **Preferencias**: ajustes como **modo oscuro** desde `PreferenceScreen`.
- **Sesión de usuario**: autenticación con Firebase UI (si está configurado el proyecto con `google-services.json`).

---

## APIs / integraciones

- **Geo API** (búsqueda de ciudades): endpoint tipo `v1/search` (Retrofit).
- **Weather API** (forecast / current): endpoint tipo `v1/forecast` (Retrofit).
- **Nominatim** (geocoding / reverse geocoding): cliente dedicado en `network/NominatimAPI/*` (según estructura del paquete).
- **Google Location Services**: ubicación del dispositivo.
- **Firebase**:
  - Authentication (`firebase-auth`, `firebase-ui-auth`)
  - Realtime Database (`firebase-database`)

---

## Stack técnico

- **Android** (Java 11)
- **Gradle** (módulo `:app`)
- **Retrofit 2 + Gson converter**
- **Room**
- **Lifecycle**: ViewModel + LiveData
- **WorkManager** (incluido como dependencia)
- **Firebase Auth / Realtime Database**
- **Material Components**

> SDK: minSdk 29, targetSdk 36 (ver `app/build.gradle`).

---

## Estructura (alto nivel)

- `app/src/main/java/.../ui/activities/`
  - `MainActivity`: pantalla principal (búsqueda, ubicación, consulta, etc.)
- `app/src/main/java/.../network/`
  - Servicios Retrofit por API (Geo, Weather, Nominatim)
- `app/src/main/java/.../models/`
  - Modelos POJO para parsear respuestas JSON (Gson)
- `app/src/main/java/.../models/local/`
  - Persistencia local (Room) + repositorio de consultas
- `app/src/main/java/.../ui/viewmodel/`
  - ViewModels (MVVM)
- `app/src/main/res/xml/`
  - Preferencias (modo oscuro)
- `app/src/main/res/values/`
  - Strings/colores

---

## Cómo ejecutar (rápido)

1. Abrir el proyecto en **Android Studio**.
2. Sincronizar Gradle.
3. (Si aplica) configurar Firebase en el proyecto:
   - Añadir `google-services.json` en `app/`
   - Verificar dependencias y configuración en consola Firebase
4. Ejecutar en emulador/dispositivo (Android 10+ por minSdk 29).

---

## Próximos pasos (ideas de mejora)

- Tests unitarios/UI para la capa de red y pantallas.
- Manejo más robusto de errores (offline, timeouts, estados vacíos).
- Cache de respuestas meteorológicas con expiración.
- Separar aún más capas (UseCases/Domain) si se busca Clean Architecture completa.
- CI/CD (GitHub Actions) para build + lint + tests.

---
**Autor:** @rndymi
