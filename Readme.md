# 🚀 Mini Notion Backend

A **production-ready Spring Boot backend** inspired by Notion’s building blocks — **Tasks, Categories, Comments, Users, and Attachments**.
It runs on **PostgreSQL via Docker**, follows a **modular domain-driven architecture**, and is designed to integrate seamlessly with a **React frontend**.

This project is not just a demo — it’s a **blueprint for scalable Java backends** that you can fork, extend, and deploy today.

---

## ✨ Features

✅ Modular architecture (`task`, `comment`, `category`, `attachment`, `user`)
✅ Clean separation of **Domain, Application, Infrastructure**
✅ PostgreSQL with `docker-compose`
✅ Gradle build system (wrapper included)
✅ Config profiles: `dev`, `test`, `prod`
✅ **React-ready REST API** (CORS configured)
✅ Attachment module pluggable with **S3 / MinIO**

---

## 📂 Project Structure

```text
src/main/java/dev/amirgol/springtaskbackend/
├── attachment/      # File uploads & storage (S3/MinIO ready)
├── category/        # Task categories
├── comment/         # Commenting system for tasks
├── core/            # Exceptions, core utilities
├── shared/          # Shared components, cross-cutting concerns
├── task/            # Task management (CRUD, deadlines, assignment)
├── user/            # User management (auth-ready)
└── App.java         # Application entry point
```

**Inside each module**:

* **Domain** → Entities, rules, value objects
* **Application** → Use cases, DTOs, ports
* **Infrastructure** → Persistence, adapters, config

---

## 🛠 Prerequisites

* [Java 17+](https://adoptium.net/)
* [Gradle 8+](https://gradle.org/install/) (or use wrapper)
* [Docker & Docker Compose](https://docs.docker.com/compose/install/)
* [Node.js + npm](https://nodejs.org/) (for React frontend)

---

## 🐘 Start PostgreSQL

```bash
docker-compose up -d
docker-compose ps
```

Database credentials match `application.yml` and `docker-compose.yml`.

---

## ▶️ Run the Backend

```bash
./gradlew bootRun
```

On Windows:

```bash
gradlew.bat bootRun
```

Backend runs at:

```http
http://localhost:8080
```

Try hitting:

```http
http://localhost:8080/api/tasks
```

---

## ⚙️ Config Profiles

* `application.yml` → Base config
* `application-dev.yml` → Development
* `application-test.yml` → Testing
* `application-prod.yml` → Production

Switch with:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

---

## 📦 Build & Test

Build JAR:

```bash
./gradlew build
```

Run tests:

```bash
./gradlew test
```

---

## 🌐 React Frontend

The backend is designed to be consumed by a React app.

1. Clone the React frontend (coming soon in `/frontend`).

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start React:

   ```bash
   npm start
   ```

React runs on **[http://localhost:3000](http://localhost:3000)** and communicates with the backend on **[http://localhost:8080](http://localhost:8080)**.

CORS is enabled for local development by default.

---

## 🚧 Roadmap

* 🔒 JWT authentication + refresh tokens
* 📎 File attachment cloud storage integration
* 📡 GraphQL API option (Netflix DGS)
* 📊 Observability with Prometheus + Grafana
* ☁️ Docker + Kubernetes deployment

---

## 🤝 Contributing

1. Fork the repo
2. Create your feature branch (`git checkout -b feature/amazing`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push (`git push origin feature/amazing`)
5. Open a Pull Request

---

## 📜 License

MIT — open, free, and reusable.

---

🔥 With **Mini Notion Backend**, you don’t just run a project — you get a **reference architecture for scalable React + Spring Boot apps**.
