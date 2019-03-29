FROM tomcat:8.0.20-jre8
ENV JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=docker"
RUN export JAVA_OPTS="$JAVA_OPTS"
RUN rm -r /usr/local/tomcat/webapps/ROOT/
COPY /controller/target/controller-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war