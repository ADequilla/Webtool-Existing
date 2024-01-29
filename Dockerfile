# Use a base image with Tomcat installed
FROM tomcat:9.0-jdk11-openjdk-slim

# Remove the default Tomcat webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file into the Tomcat webapps directory
COPY webtool.war /usr/local/tomcat/webapps/webtool.war

# Expose the default Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
