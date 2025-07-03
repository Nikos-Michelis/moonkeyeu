# Backend configuration Spring Boot 3.3.4 - Java 17

1. Make sure you have **Docker** installed on your system.
 ```bash
  docker --version
 ```
2. Create the environment variables according to **env.example** file.
3. Make sure the project profile is set to the **production profile**.
4. Open a terminal in the project directory and run the following command to build and start the application:

```bash
  docker-compose up --build -d
 ```
## 🛠️ Related dependencies 

1. **Spring Security 6.1**  
   Provides authentication and authorization mechanisms to secure the backend API.

2. **Spring Data JPA**  
   Simplifies database interactions using repository abstractions for CRUD operations and custom queries.

3. **Caffeine (In-Memory Caching)**  
   High-performance local caching to reduce database load and improve response times for frequently accessed data.

4. **WebClient**  
   Reactive, non-blocking HTTP client used for data fetching from external APIs.

5. **Spring Batch**  
   Powers the ETL (Extract, Transform, Load) pipeline. Handles large-scale data processing tasks with support for scheduling, retries, chunk-based processing, and transactional integrity.