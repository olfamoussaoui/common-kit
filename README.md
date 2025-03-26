# 🧰 common-kit

A lightweight and extensible Java utility toolkit designed for modern backend applications.

`common-kit` provides a reusable foundation for microservices or monolithic systems by centralizing common infrastructure concerns such as HTTP clients, authentication strategies, base repositories, observability, and utility helpers.

---

## 🌟 Key Features

✅ **Generic HTTP client** using Spring WebClient  
✅ **Pluggable authentication strategies**: JWT, Basic Auth, HMAC  
✅ **Base repository abstraction** with auditing support  
✅ **Centralized error handling** and API response wrappers  
✅ **Correlation ID tracing** for observability  
✅ **Reusable utils**: date/time, string formatting, validation, etc.

---

## 📦 Package Structure
    common/
    ├── db/
    │   ├── BaseRepository.java
    │   ├── BaseEntity.java
    │   └── JpaSpecificationUtils.java
    │
    ├── security/
    │   ├── JwtTokenProvider.java
    │   ├── JwtAuthenticationFilter.java
    │   └── Role.java
    │
    ├── rest/
    │   ├── ApiResponse.java
    │   ├── GlobalExceptionHandler.java
    │   └── ErrorResponse.java
    │
    ├── logging/
    │   ├── CorrelationIdFilter.java
    │   └── RequestLogger.java
    │
    ├── utils/
    │   ├── DateTimeUtils.java
    │   ├── GeoUtils.java
    │   └── ValidationUtils.java
    │
    └── config/
        ├── OpenApiConfig.java
        ├── ObjectMapperConfig.java
        ├── CorsConfig.java
        └── ...

## 🛠 Technologies

- Java 17+
- Spring Boot / Spring WebFlux
- WebClient (non-blocking HTTP client)
- Spring Security
- Jackson (JSON serialization)
- Lombok 

👤 Author
Crafted with ❤️ by [Olfa MOUSSAOUI]
Contact: [moussaoui.olfa1@gmail.com] • GitHub: [@olfamoussaoui]
