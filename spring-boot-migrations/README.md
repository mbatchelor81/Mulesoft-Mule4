# Spring Boot Migrations

MuleSoft Mule 4 applications migrated to Spring Boot 3.2.x.

## Project Structure

```
spring-boot-migrations/
├── pom.xml                      # Parent POM (Spring Boot 3.2.x)
├── common-security/             # Shared Azure AD JWT security library
├── docker-compose.yml           # Oracle DB, ActiveMQ, FTP for local dev
│
│── WAVE 1 — Foundations & Standalone
├── emp-sapi/                    # Simple greeting endpoint
├── prop-demo/                   # Spring profiles & ConfigurationProperties
├── secure-prop-demo/            # Azure Key Vault integration
├── test-flows/                  # @Async, service composition, variable scoping
├── dataweave-samples/           # DataWeave → Jackson/POI transformations
├── emp-hr-sapi/                 # RAML→OpenAPI employee HR API (stubs)
├── ftp-app/                     # Scheduled FTP file reader
│
│── WAVE 2 — Core DB & JMS Patterns
├── emp-sapi-v1/                 # Full CRUD REST API with JPA + Oracle
├── jms-app/                     # JMS queue & topic listeners
├── soap-service/                # Spring WS SOAP service provider
├── ws-consumer-demo/            # SOAP client (Calculator service)
│
│── WAVE 3 — File Integrations & API Gateway
├── emp-csv-database/            # Spring Batch CSV→DB import
├── database-to-ftp-excel/       # Scheduled DB→XLSX→FTP export
├── emp-onboard-api/             # Employee onboarding API with error handling
│
│── WAVE 4 — Service Chaining & Protocol Bridging
├── jms-push-operations/         # HTTP→JMS bridge with listener
├── consume-json-rest-service/   # Service chaining with WebClient
├── error-handling-demo/         # @ControllerAdvice error handling patterns
│
│── WAVE 5 — Complex Orchestration
├── rest-over-jms/               # JMS→REST with JSON↔XML transform
└── mule-scope-demo/             # Retry, Batch, Parallel, ForEach patterns
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (for local infrastructure)

## Quick Start

### 1. Start Infrastructure

```bash
cd spring-boot-migrations
docker compose up -d
```

This starts:
- **Oracle DB** on port 1521 (system/oracle)
- **ActiveMQ** on port 61616 (admin console at http://localhost:8161, admin/admin)
- **FTP Server** on port 21 (ftpuser/ftpuser)

### 2. Build All Modules

```bash
mvn clean package -DskipTests
```

### 3. Run Individual Apps

```bash
# Example: Run emp-sapi
mvn spring-boot:run -f emp-sapi/pom.xml

# Example: Run prop-demo with 'dev' profile
mvn spring-boot:run -f prop-demo/pom.xml -Dspring-boot.run.profiles=dev
```

### 4. Run Tests

```bash
# All tests
mvn test

# Single module
mvn test -f emp-sapi/pom.xml
```

## Azure AD Setup

### App Registration

1. Register a new app in Azure AD (Entra ID)
2. Configure the following:
   - **Application (client) ID** → `AZURE_AD_CLIENT_ID`
   - **Client Secret** → `AZURE_AD_CLIENT_SECRET`
   - **Directory (tenant) ID** → `AZURE_AD_TENANT_ID`
3. Expose an API with scope `Employee.ReadWrite`
4. Set environment variables or update `application-security.yml`

### Environment Variables

```bash
export AZURE_AD_CLIENT_ID=your-client-id
export AZURE_AD_CLIENT_SECRET=your-client-secret
export AZURE_AD_TENANT_ID=your-tenant-id
export AZURE_AD_APP_ID_URI=api://your-app-id
```

## Database Schema

The following tables are referenced across applications:

```sql
CREATE TABLE emp (
    emp_id      NUMBER PRIMARY KEY,
    emp_name    VARCHAR2(100),
    emp_status  VARCHAR2(1),
    emp_salary  NUMBER(10,2),
    emp_appr_per NUMBER(5,2)
);

CREATE TABLE emp_master (
    emp_id      NUMBER PRIMARY KEY,
    emp_name    VARCHAR2(100),
    emp_sal     NUMBER(10,2),
    emp_status  VARCHAR2(1)
);

CREATE TABLE emp_masterv2 (
    emp_id      NUMBER PRIMARY KEY,
    emp_name    VARCHAR2(100),
    emp_status  VARCHAR2(1)
);

CREATE TABLE emp_fin_master (
    emp_id      NUMBER PRIMARY KEY,
    emp_name    VARCHAR2(100),
    emp_status  VARCHAR2(1)
);

CREATE TABLE emp_appr_master (
    emp_id      NUMBER PRIMARY KEY,
    emp_name    VARCHAR2(100),
    emp_status  VARCHAR2(1)
);

CREATE TABLE emp_backup (
    emp_id        NUMBER,
    emp_name      VARCHAR2(100),
    emp_salary    NUMBER(10,2),
    increment_sal NUMBER(10,2)
);
```

## ActiveMQ / Azure Service Bus

### Local ActiveMQ

ActiveMQ is provided via Docker Compose. Queues and topics used:
- **Queue**: `Q.FSD.EMP`
- **Topic**: `T.FSD.EMP`

### Azure Service Bus (Production)

Replace `spring.activemq` config with Azure Service Bus:
```yaml
spring:
  jms:
    servicebus:
      connection-string: ${AZURE_SERVICEBUS_CONNECTION_STRING}
      pricing-tier: standard
```

Add dependency:
```xml
<dependency>
    <groupId>com.azure.spring</groupId>
    <artifactId>spring-cloud-azure-starter-servicebus-jms</artifactId>
</dependency>
```

## Module Port Assignments

| Module | Port | Description |
|--------|------|-------------|
| emp-sapi | 8081 | Simple greeting |
| prop-demo | 8082 | Property demo |
| secure-prop-demo | 8083 | Key Vault demo |
| test-flows | 8084 | Async/flow demo |
| dataweave-samples | 8085 | Transformation demo |
| emp-hr-sapi | 8086 | HR API (stubs) |
| emp-sapi-v1 | 8087 | CRUD API |
| soap-service | 8088 | SOAP provider |
| ws-consumer-demo | 8089 | SOAP consumer |
| emp-csv-database | 8090 | CSV import |
| database-to-ftp-excel | 8091 | DB→FTP export |
| emp-onboard-api | 8092 | Onboarding API |
| jms-push-operations | 8093 | HTTP→JMS |
| consume-json-rest-service | 8094 | Service chaining |
| error-handling-demo | 8095 | Error handling |
| rest-over-jms | 8096 | JMS→REST |
| mule-scope-demo | 8097 | Orchestration patterns |
