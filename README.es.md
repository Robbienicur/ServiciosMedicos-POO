<p align="center">
  <img src="src/icons/udlap_logo.png" alt="UDLAP Logo" width="120"/>
</p>

<h1 align="center">Servicios Medicos UDLAP</h1>

<p align="center">
  <strong>Sistema de Gestion de Servicios Medicos para la Universidad de las Americas Puebla</strong>
</p>

<p align="center">
  <a href="#-demo">Demo</a> &bull;
  <a href="#-acerca-de">Acerca de</a> &bull;
  <a href="#-funcionalidades">Funcionalidades</a> &bull;
  <a href="#-tecnologias">Tecnologias</a> &bull;
  <a href="#-como-ejecutar">Como Ejecutar</a> &bull;
  <a href="#-proyecto-original">Proyecto Original</a> &bull;
  <a href="#-equipo">Equipo</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white" alt="SQLite"/>
  <img src="https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white" alt="Swing"/>
  <img src="https://img.shields.io/badge/FlatLaf-4CAF50?style=for-the-badge&logoColor=white" alt="FlatLaf"/>
</p>

<p align="center">
  <a href="README.md">
    <img src="https://img.shields.io/badge/English-blue?style=flat-square" alt="English"/>
  </a>
  &nbsp;
  <a href="README.es.md">
    <img src="https://img.shields.io/badge/Español-red?style=flat-square" alt="Español"/>
  </a>
</p>

---

## Demo

https://github.com/user-attachments/assets/demo.mov

> Si el video no carga, descargalo directamente desde [`assets/demo.mov`](assets/demo.mov).

---

## Acerca de

**Servicios Medicos UDLAP** es una aplicacion de escritorio para la gestion del departamento de servicios medicos de la Universidad de las Americas Puebla. Proporciona interfaces separadas para medicos y pacientes, cubriendo el flujo completo desde el registro de pacientes hasta la gestion de emergencias.

Este proyecto es una **continuacion y rediseno completo de la interfaz** del [proyecto original](https://github.com/marianafm12/ServiciosMedicos-POO) desarrollado en equipo para la materia de **Programacion Orientada a Objetos (POO)** en la UDLAP. Contribui al codigo original y continue de manera independiente para **redisenar toda la interfaz de usuario** usando principios de diseno modernos, FlatLaf Look & Feel, y un sistema de diseno personalizado con la marca institucional UDLAP.

### Que Cambio

| Aspecto | Original | Rediseño |
|---------|----------|----------|
| Look & Feel | Swing por defecto | FlatLaf Light |
| Sistema de colores | Colores hardcodeados | Tokens de diseno UDLAP centralizados |
| Navegacion | Botones verdes/naranjas alternados | Sidebar blanco limpio con estados activos |
| Formularios | Campos basicos de Swing | Estilo Material con indicadores de foco |
| Botones | JButtons sin estilo | Sistema de 3 variantes (primario, secundario, neutro) |
| Componentes | Paneles planos | Cards redondeadas con sombras sutiles |
| Ventana | Frame sin decoracion personalizado | Ventana nativa decorada (moderna, redimensionable) |

---

## Funcionalidades

### Para Medicos

- **Registro de Pacientes** — Formulario completo de datos clinicos con validacion
- **Nueva Consulta** — Auto-llenado de datos del paciente, sintomas, diagnostico, recetas
- **Historial Medico** — Busqueda y visualizacion de expedientes con consultas expandibles
- **Justificantes Medicos** — Revisar, aprobar o rechazar solicitudes de justificantes
- **Gestion de Emergencias** — Registro de llamadas de emergencia y reportes de accidentes

### Para Pacientes

- **Gestion de Citas** — Agendar y modificar citas medicas
- **Historial Medico** — Ver expediente personal y consultas pasadas
- **Solicitar Justificantes** — Enviar y dar seguimiento a solicitudes de justificantes
- **Reportar Emergencia** — Acceso rapido a servicios medicos y seguridad del campus

---

## Tecnologias

| Componente | Tecnologia |
|-----------|-----------|
| Lenguaje | Java 8+ |
| Framework GUI | Swing + [FlatLaf](https://www.formdev.com/flatlaf/) 3.4.1 |
| Base de datos | SQLite (JDBC) |
| Generacion de PDF | iText 2.1.7 |
| Sistema de diseno | Personalizado (BotonUDLAP, CampoTextoUDLAP, CardPanel, SidebarPanel) |

---

## Como Ejecutar

### Requisitos

- Java JDK 8 o superior

### Ejecucion

```bash
# Clonar el repositorio
git clone https://github.com/Robbienicur/ServiciosMedicos-POO.git
cd ServiciosMedicos-POO

# Compilar
mkdir -p out
javac -cp "lib/*" -d out $(find src -name "*.java")

# Ejecutar
java -cp "out:lib/*" Inicio.InterfazLogin
```

### Credenciales de Prueba

| Rol | ID | Contrasena |
|-----|----|------------|
| Medico | 5009 | pass5009 |
| Paciente | 180000 | pass180000 |

---

## Estructura del Proyecto

```
src/
├── BaseDeDatos/        # Conexion y consultas a SQLite
├── Consultas/          # Gestion de consultas medicas
├── Emergencias/        # Llamadas de emergencia y reportes de accidentes
├── GestionCitas/       # Agendamiento y modificacion de citas
├── GestionEnfermedades/# Historial medico y expedientes
├── Inicio/             # Login, interfaz principal, sesion
├── Justificantes/      # Solicitudes y aprobacion de justificantes
├── Modelo/             # Modelos de datos
├── Registro/           # Registro de pacientes
├── Utilidades/         # Utilidades compartidas y sistema de diseno
│   └── ui/             # Componentes UI personalizados (nuevo)
└── icons/              # Iconos de la aplicacion
```

---

## Proyecto Original

Este repositorio es una continuacion del proyecto original en equipo:

> **[marianafm12/ServiciosMedicos-POO](https://github.com/marianafm12/ServiciosMedicos-POO)**

El proyecto original fue desarrollado colaborativamente para la materia de Programacion Orientada a Objetos en la UDLAP. Fui colaborador activo en el codigo original y continue de manera independiente para implementar un rediseno completo de la interfaz con un sistema de diseno moderno, componentes reutilizables personalizados y branding institucional UDLAP.

---

## Equipo

### Equipo Original (Materia POO)

| Nombre | Area |
|--------|------|
| Mariana Fernandez | Base de datos, Consultas, Emergencias, UI Base |
| Robbie Nicolas Curioso de Salazar | Historial Medico, Consultas |
| Arlette | Citas, Mejoras de UI |
| Eduardo | Justificantes Medicos |
| Sebastian | Registro de Pacientes |

### Rediseno de UI

| Nombre | Contribucion |
|--------|-------------|
| Robbie Nicolas Curioso de Salazar | Rediseno e implementacion completa de UI/UX |

---

## Licencia

Este proyecto fue desarrollado con fines academicos en la Universidad de las Americas Puebla (UDLAP).
