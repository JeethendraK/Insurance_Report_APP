# Use an official Tomcat base image with Java 17
FROM tomcat:jre17-temurin

# Remove the default Tomcat webapps to avoid conflicts
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the Spring Boot WAR file to the Tomcat webapps directory
COPY target/Report_App.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Start the Tomcat server
CMD ["catalina.sh", "run"]
