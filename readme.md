/mnt/c/Users/porti/Desktop/Projects/Practice/integration
docker run -p 8080:8080 pablo/integration:1.0
nexus
    docker run -d -p 8081:8081 --name nexus sonatype/nexus3
    http://localhost:8081
    docker exec nexus cat /nexus-data/admin.password
    -> http://localhost:8081 admin/ y la pass de arriba
    te creas un repo en nexus
| Campo           | Valor                        |
| --------------- | ---------------------------- |
| Name            | docker-hosted                |
| HTTP Port       | **8083** (o cualquier libre) |
| Allow anonymous | ❌ NO                         |
    docker ps: ver que tienes levantado el 8083
    docker login localhost:8083
        admin/pass que has reiniciado
        "Login succeeded"

    push del micro
        docker tag pablo/integration:1.0 localhost:8083/integration:1.0 (etiquetar la imagen)
        docker push localhost:8083/integration:1.0 (subirla al registry)

    
JENKINS
docker run -d \
--name jenkins \
-p 8080:8080 -p 50000:50000 \
-v jenkins_home:/var/jenkins_home \
jenkins/jenkins:lts

    docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
    http://localhost:8080 y metes la pass
    instalas los pluggins por defecto
