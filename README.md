<p align="center">
  <img src="src/icons/udlap_logo.png" alt="UDLAP Logo" width="120"/>
</p>

<h1 align="center">Servicios Medicos UDLAP</h1>

<p align="center">
  <strong>Medical Services Management System for Universidad de las Americas Puebla</strong>
</p>

<p align="center">
  <a href="#-demo">Demo</a> &bull;
  <a href="#-about">About</a> &bull;
  <a href="#-features">Features</a> &bull;
  <a href="#-tech-stack">Tech Stack</a> &bull;
  <a href="#-getting-started">Getting Started</a> &bull;
  <a href="#-original-project">Original Project</a> &bull;
  <a href="#-team">Team</a>
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

<!-- PASTE VIDEO URL HERE -->





---

## About

**Servicios Medicos UDLAP** is a desktop application for managing the medical services department at Universidad de las Americas Puebla. It provides separate interfaces for doctors and patients, covering the full workflow from patient registration to emergency management.

This project is a **continuation and complete UI redesign** of the [original project](https://github.com/marianafm12/ServiciosMedicos-POO) developed as a team for the **Object-Oriented Programming (POO)** course at UDLAP. I contributed to the original codebase and then continued independently to **redesign the entire user interface** using modern design principles, FlatLaf Look & Feel, and a custom UDLAP-branded design system.

### What Changed

| Aspect | Original | Redesigned |
|--------|----------|------------|
| Look & Feel | Default Java Swing | FlatLaf Light |
| Color System | Hardcoded inline colors | Centralized UDLAP design tokens |
| Navigation | Alternating green/orange buttons | Clean white sidebar with active states |
| Forms | Basic Swing fields | Material-style inputs with focus indicators |
| Buttons | Raw JButtons | 3-variant system (primary, secondary, neutral) |
| Components | Plain panels | Rounded cards with subtle shadows |
| Window Chrome | Custom undecorated frame | Native decorated (modern, resizable) |

---

## Features

### For Doctors

- **Patient Registration** — Complete clinical data form with validation
- **New Consultation** — Auto-fill patient data, symptoms, diagnosis, prescriptions
- **Medical History** — Search and view patient records with expandable consultation cards
- **Medical Justifications** — Review, approve, or reject absence justification requests
- **Emergency Management** — Register emergency calls and detailed accident reports

### For Patients

- **Appointment Management** — Schedule and modify medical appointments
- **Medical History** — View personal health records and past consultations
- **Request Justifications** — Submit and track medical justification requests
- **Report Emergency** — Quick access to medical services and campus security

---

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 8+ |
| GUI Framework | Swing + [FlatLaf](https://www.formdev.com/flatlaf/) 3.4.1 |
| Database | SQLite (JDBC) |
| PDF Generation | iText 2.1.7 |
| Design System | Custom (BotonUDLAP, CampoTextoUDLAP, CardPanel, SidebarPanel) |

---

## Getting Started

### Prerequisites

- Java JDK 8 or higher

### Run

```bash
# Clone the repository
git clone https://github.com/Robbienicur/ServiciosMedicos-POO.git
cd ServiciosMedicos-POO

# Compile
mkdir -p out
javac -cp "lib/*" -d out $(find src -name "*.java")

# Run
java -cp "out:lib/*" Inicio.InterfazLogin
```

### Test Credentials

| Role | ID | Password |
|------|----|----------|
| Doctor | 5009 | pass5009 |
| Patient | 180000 | pass180000 |

---

## Project Structure

```
src/
├── BaseDeDatos/        # SQLite database connection and queries
├── Consultas/          # Medical consultation management
├── Emergencias/        # Emergency calls and accident reports
├── GestionCitas/       # Appointment scheduling and modification
├── GestionEnfermedades/# Medical history and patient records
├── Inicio/             # Login, main interface, session management
├── Justificantes/      # Medical justification requests and approvals
├── Modelo/             # Data models
├── Registro/           # Patient registration
├── Utilidades/         # Shared utilities and design system
│   └── ui/             # Custom UI components (new)
└── icons/              # Application icons
```

---

## Original Project

This repository is a continuation of the original team project:

> **[marianafm12/ServiciosMedicos-POO](https://github.com/marianafm12/ServiciosMedicos-POO)**

The original project was developed collaboratively for the Object-Oriented Programming course at UDLAP. I was an active contributor to the original codebase and continued independently to implement a complete UI overhaul with a modern design system, custom reusable components, and institutional UDLAP branding.

---

## Team

### Original Team (POO Course)

| Name | Area |
|------|------|
| Mariana Fernandez | Database, Consultations, Emergencies, UI Base |
| Robbie Nicolas Curioso de Salazar | Medical History, Consultations |
| Arlette | Appointments, UI Improvements |
| Eduardo | Medical Justifications |
| Sebastian | Patient Registration |

### UI Redesign

| Name | Contribution |
|------|-------------|
| Robbie Nicolas Curioso de Salazar | Complete UI/UX redesign and implementation |

---

## License

This project was developed for academic purposes at Universidad de las Americas Puebla (UDLAP).
