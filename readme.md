Una vez que tenemos el micro funcionando
1) 
Crear imagen
te vas a tu proyecto ->/mnt/c/Users/porti/Desktop/Projects/Practice/integration
docker build -t integration:1.0 .

Correr contenedor 
docker run -p 8080:8080 pablo/integration:1.0


2) Levantar  Nexus (Docker)
docker run -d -p 8081:8081 -p 8083:8083 --name nexus \ -v nexus-data:/nexus-data \ sonatype/nexus3
esperamos un poco
docker exec nexus cat /nexus-data/admin.password
http://localhost:8081 : admin/pass anterior
Te creas un repositorio: settings/create repository

| Campo           | Valor                        |
| --------------- | ---------------------------- |
| Name            | docker-hosted                |
| HTTP Port       | **8083** (o cualquier libre) |
| Allow anonymous | ❌ NO                         |

docker login localhost:8083
admin/pass que has reiniciado
"Login succeeded"

tag de la imagen para apuntar al repositorio: docker tag pablo/integration:1.0 localhost:8083/integration:1.0 (etiquetar la imagen)
push: docker push localhost:8083/integration:1.0 (subirla al registry)

3) JENKINS
   docker run -d -p 8080:8080 -p 50000:50000 --name jenkins \
   -v jenkins_home:/var/jenkins_home \
   jenkins/jenkins:lts
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
http://localhost:8080 y metes la pass
instalas los pluggins por defecto

settings y añadimos mvn

configurar acceso a docker dentro del jenkins container: docker build y docker push
docker stop jenkins
docker rm jenkins
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins \
-v jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
jenkins/jenkins:lts

Pipeline
En Jenkins, ve a “New Item” → ponle un nombre, por ejemplo integracion-completa.
Selecciona Pipeline → clic en OK.
En la sección Pipeline:
Definition: Pipeline script from SCM
SCM: Git
Repository URL: https://github.com/portix4/complete-integration.git
Branch: main
Credentials: deja vacío si es público


b9a243fe68724c86bbe67f91a1307df5

CI
sudo snap install kubectl --classic (k8s en docker)
k3d cluster create demo2 --api-port 6552 -p "8085:80@loadbalancer" (crear cluster)
kubectl get nodes (verificar)
NAME                 STATUS   ROLES                  AGE   VERSION
k3d-demo3-server-0   Ready    control-plane,master   23s   v1.31.5+k3s1

crear /k8s 
    - deployment.yaml
    - service.yaml

Credencial jenkins
Jenkins
Administrar Jenkins
Credentials
System
Global credentials (unrestricted)
    Tipo: Username with password
    Username: admin
    Password: tu contraseña de Nexus
    ID: nexus-creds

pipeline en jenkins
Tipo: Pipeline
Repo: https://github.com/portix4/complete-integration.git
Pipeline script from SCM → Branch: main
Script Path: Jenkinsfile (lo crearemos en el siguiente paso)

crear Jenkinsfile

Guardar todo en el repo u ejecutar jenkins