# Testing Mulesoft-Mule4 Applications

## Prerequisites

- **Java 8** (OpenJDK 1.8.x)
- **Maven 3.6+**
- **Docker** (for running Mule apps locally)
- **~/.m2/settings.xml** configured with MuleSoft repositories (Anypoint Exchange, MuleSoft releases/snapshots). This is set up automatically via the environment config.

## Build Commands

```bash
# Build all 17 projects from root
mvn clean package -B

# Build a single project
mvn clean package -B -f emp-sapi/pom.xml

# Use the convenience script
./scripts/build-all.sh
```

## Running an App Locally

Apps run inside a Docker container using the `neerajeai/mule4:4.3.0-ea2` image (Mule Runtime 4.3.0 CE).

```bash
# Using the convenience script (builds if needed)
./scripts/run-app.sh emp-sapi 8081

# Or manually
docker run -d --name mule4-emp-sapi \
  -p 8081:8081 \
  -v $(pwd)/emp-sapi/target/emp-sapi-1.0.0-SNAPSHOT-mule-application.jar:/opt/mule/apps/app.jar \
  neerajeai/mule4:4.3.0-ea2
```

Wait ~25-30 seconds for the Mule runtime to fully start. Check logs with `docker logs mule4-emp-sapi`.

### Verifying emp-sapi

```bash
curl http://localhost:8081/emp-sapi/hello
# Expected: HTTP 200
```

## MUnit Tests

MUnit test infrastructure is configured in `emp-sapi` and `dataweave_samples` but **tests are skipped by default** (`skipMunitTests=true`). Running them requires MuleSoft Enterprise repository access for the `mule-runtime-impl-bom` artifact.

```bash
# To run MUnit tests (requires MuleSoft EE repo credentials)
mvn test -f emp-sapi/pom.xml -DskipMunitTests=false
```

## Known Limitations

- **soap-service** is excluded from the parent POM — it requires MuleSoft Enterprise credentials for `mule-soapkit-cxf`
- **emp-hr-sapi** has no `pom.xml` — it contains only RAML specs and examples
- **MUnit embedded container** needs `mule-runtime-impl-bom` from MuleSoft's private repo (`https://repository.mulesoft.org/nexus/content/repositories/private/`) which returns 401 without credentials
- The Docker image `neerajeai/mule4:4.3.0-ea2` is a community image — for production use, consider MuleSoft's official runtime

## Project Structure

- 17 buildable Mule 4 projects, each with their own `pom.xml`
- Root `pom.xml` is a parent aggregator that builds all modules
- Most projects use Mule Runtime 4.3.0 (`ws-consumer-demo` uses 4.2.0, `dataweave_samples` uses 4.3.0-20200824)
- All projects produce `*-mule-application.jar` artifacts in their `target/` directory

## Devin Secrets Needed

No secrets are required for basic build and Docker-based runtime testing. MuleSoft Enterprise credentials would be needed only for:
- Running MUnit tests (`-DskipMunitTests=false`)
- Building `soap-service`
