<!-- banner-badges -->
<p align="center">
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/" target="_blank" rel="noopener noreferrer">
    <img width="100%" src="docs/img/banner.gif" alt="Realtime Gateway — Maximiliano Rodrigo Soria">
  </a>
</p>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/github/license/MaximilianoRodrigoSoria/realtime-gateway?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="License"></a>
  <img src="https://img.shields.io/github/last-commit/MaximilianoRodrigoSoria/realtime-gateway?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Last commit">
  <img src="https://img.shields.io/github/repo-size/MaximilianoRodrigoSoria/realtime-gateway?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Repo size">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-06C69C?style=flat-square&labelColor=1A1C1F&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/WebSocket-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=socketdotio&logoColor=white" alt="WebSocket">
  <img src="https://img.shields.io/badge/STOMP-•-06C69C?style=flat-square&labelColor=1A1C1F" alt="STOMP">
  <img src="https://img.shields.io/badge/Redis-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/PostgreSQL-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=postgresql&logoColor=white" alt="PostgreSQL">
</p>

# Realtime Gateway

Backend de comunicacion en tiempo real sobre WebSockets: gestiona conexiones, salas, presencia de usuarios y mensajeria, con backplane Redis para escalar horizontalmente.

> Proyecto de portafolio backend. Sigue el estandar de **arquitectura hexagonal (Ports & Adapters)**, Java 21 y Spring Boot, con quality gates (Spotless, Checkstyle, PMD, SpotBugs, ArchUnit), testing con Testcontainers y observabilidad (Micrometer + Prometheus).

## Caracteristicas

- Handshake WebSocket autenticado con JWT
- Salas: crear, unirse, salir y difundir
- Presencia con TTL + heartbeat y typing indicators
- Mensajeria directa (1:1) y grupal con historial persistente
- Notificaciones en tiempo real
- Backplane Redis Pub/Sub para multiples instancias
- Entrega offline y reconexion
- Metricas de conexiones y latencia de entrega

## Stack

Java 21 · WebSocket · STOMP · Redis · PostgreSQL · Gradle · Flyway · Docker · JUnit 5 · Testcontainers

## Arquitectura

Organizado por **feature** en capas `domain -> application -> infrastructure`, con la regla de dependencia verificada por ArchUnit. La logica de negocio (dominio y casos de uso) no depende de framework ni de infraestructura; los adaptadores (web, persistencia, mensajeria) implementan puertos definidos por la aplicacion.

## Estado

🚧 En planificacion / arranque. El diseno detallado (epicas, historias y criterios de aceptacion) vive en el plan del portafolio.

---

<p align="center">
  <strong>Maximiliano Rodrigo Soria</strong><br>
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/">LinkedIn</a> · <a href="mailto:maximilianorodrigosoria@gmail.com">maximilianorodrigosoria@gmail.com</a>
</p>
