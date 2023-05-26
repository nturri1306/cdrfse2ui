
FROM adoptopenjdk/openjdk11:latest

RUN apt-get update && \
    apt-get install -y iputils-ping net-tools telnet && \
    apt-get clean

WORKDIR /app

COPY misc/3872.jks /app/3872.jks
COPY misc/cacerts /app/cacerts
COPY misc/CDR-ROC-AD-01-AffinityDomain-Rev-1.11.xml /app/CDR-ROC-AD-01-AffinityDomain-Rev-1.11.xml
COPY misc/users.properties /app/users.properties
COPY cdrfse2ui.war /app/

EXPOSE 7080

CMD  java -jar  cdrfse2ui.war

#--debug

