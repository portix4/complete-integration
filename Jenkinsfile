pipeline {
    agent any
    environment {
        NEXUS = "localhost:8083"
        IMAGE_NAME = "integration"
        IMAGE_TAG = "1.0"
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/portix4/complete-integration.git'
            }
        }
		stage('Build Maven') {
			steps {
				withMaven(maven: 'Maven 3.9.9') {
					sh 'mvn clean package -DskipTests'
				}
			}
		}
        stage('Docker Build') {
            steps {
                sh "docker build -t $NEXUS/$IMAGE_NAME:$IMAGE_TAG ."
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | docker login $NEXUS -u $USER --password-stdin'
                }
            }
        }
        stage('Docker Push') {
            steps {
                sh "docker push $NEXUS/$IMAGE_NAME:$IMAGE_TAG"
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh """
                kubectl apply -f k8s/deployment.yaml
                kubectl apply -f k8s/service.yaml
                """
            }
        }
    }
}
