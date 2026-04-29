FROM neerajeai/mule4:4.3.0-ea2

# Deploy a Mule application by copying the packaged JAR into the apps directory.
# Build the app first: mvn clean package -B -f <project>/pom.xml
# Then build this image:
#   docker build --build-arg APP_JAR=emp-sapi/target/emp-sapi-1.0.0-SNAPSHOT-mule-application.jar -t mule4-app .

ARG APP_JAR
COPY ${APP_JAR} /opt/mule/apps/

EXPOSE 8081
