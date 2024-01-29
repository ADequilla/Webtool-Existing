pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    // You may have Maven or Gradle build steps here
                    // For example, for Maven:
                    // sh 'mvn clean package'
                    sh 'clean install'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // Build the Docker image and tag it
                    sh 'docker build -t 95d2acb4a263 .'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    // Push the Docker image to a container registry (if needed)
                    // Replace 'your-container-registry' with the actual registry URL
                    sh 'docker push tomcat:8.5.81/95d2acb4a263'

                }
            }
        }

        stage('Deploy to Docker') {
            steps {
                script {
                    // Deploy the Docker image to your Docker environment
                    // Replace 'your-docker-image-name' with the actual image name
                    sh 'docker run -p 8080:8080 -d 95d2acb4a263'
                }
            }
        }
    }
}
