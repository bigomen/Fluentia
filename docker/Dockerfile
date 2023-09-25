FROM openjdk:11
FROM maven:latest


ENV APP_HOME /app/
ENV APPLICATION_USER fluentia-backend
RUN useradd  -ms /bin/bash $APPLICATION_USER

RUN mvn install

RUN mkdir /app

WORKDIR /app

COPY fluentia-*.jar /app/fluentia.jar
COPY docker/docker-run.sh /app/start

RUN chown -R $APPLICATION_USER /app
RUN chmod +x /app/start

ENV PATH $PATH:$APP_HOME

ENTRYPOINT ["start"]

EXPOSE 9000

USER $APPLICATION_USER