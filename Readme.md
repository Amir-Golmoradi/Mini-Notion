# Mini Notion Backend

A Spring Boot backend project for managing Tasks, Categories, Comments, and Attachments, integrated with a PostgreSQL database. The project features a modular architecture and includes a `docker-compose` setup for easy deployment.

## Prerequisites

Before running the project, ensure you have:

- [Java 17 or higher](https://adoptopenjdk.net/)
- [Gradle 8 or higher](https://gradle.org/install/) (not required if using the Gradle Wrapper)
- [Docker and Docker Compose](https://docs.docker.com/compose/install/)
- An IDE like IntelliJ IDEA or VS Code (optional but recommended)

## 1. Set Up the Database with Docker

The project includes a `docker-compose.yml` file in the root directory to start a PostgreSQL database.

1. Open a terminal and navigate to the project directory:

   ```bash
   cd path/to/my-project-repo
   ```

2. Run the following command to start the database:

   ```bash
   docker-compose up -d
   ```

   > This starts the PostgreSQL database and prepares it for the project.

3. Verify the database is running:

   ```bash
   docker-compose ps
   ```

## 2. Run the Backend

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Start the project using Gradle:

   ```bash
   ./gradlew bootRun
   ```

   > On Windows, use `gradlew.bat bootRun` instead.

3. The backend will run on **port 8080**.

    - Test it with a browser or Postman:

      ```
      http://localhost:8080/api/v1/tasks
      ```

## 3. Configuration Files

- `application.yml`: General Spring Boot configuration
- `application-dev.yml`: Development environment
- `application-prod.yml`: Production environment
- `application-test.yml`: Test environment

> The database is configured to connect to the Dockerized PostgreSQL instance by default, with credentials defined in `docker-compose.yml`.

## 4. Run the Frontend (Once Available)

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the Angular project:

   ```bash
   ng serve
   ```

    - The frontend will run on **port 4200**:

      ```
      http://localhost:4200
      ```

## 5. Project Structure

```
src/main/java/dev/amirgol/springtaskbackend/
├── Application/        # DTOs, Use Cases, and Ports
├── Domain/             # Entities and Models
├── Infrastructure/     # Adapters and Configuration
```

- **Domain**: Contains core models (Task, User, Category, Comment, Attachment)
- **Application**: Manages use cases and services
- **Infrastructure**: Handles database, repositories, and project configurations

## 6. Tips

- **Database Changes**: If you modify the database, reset the Docker Compose setup:

  ```bash
  docker-compose down -v
  docker-compose up -d
  ```

- **Build Production JAR**: Create a production-ready JAR file for the backend:

  ```bash
  ./gradlew build
  ```

- **Run Tests**: Execute automated tests:

  ```bash
  ./gradlew test
  ```

## Getting Started

This README is designed to help beginners and experienced developers alike set up and run the database, backend, and frontend with minimal effort. Follow the steps above to get started.



### Next Steps
1. **Copy the README**: Save the above content as `README.md` in the root of your GitHub repository.
2. **Push to GitHub**: Ensure the file is committed and pushed to your repository’s main branch.
3. **Optional Enhancements**: If you want to add visuals (e.g., screenshots of the running app or setup steps) or additional sections (e.g., API documentation, contribution guidelines), let me know, and I can help you expand the README with those details.