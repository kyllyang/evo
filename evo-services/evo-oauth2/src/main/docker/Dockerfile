FROM java:8
ADD ${project.build.finalName}.${project.packaging} app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Devo-appname=${project.artifactId}","-Xms512m","-Xmx512m","-Duser.timezone=GMT+08","-noverify","-jar","/app.jar"]
