pipeline {
    agent any

    environment {
        DOCKERHUB_REPO_BACK = "oussama0079/ebanking-backend"
        DOCKERHUB_REPO_FRONT = "oussama0079/ebanking-frontend"
        TAG = "v${BUILD_NUMBER}"
    }

    stages {

        stage('Build Backend') {
            steps {
                dir('ebanking-backend') {
                    sh 'mvn clean package -DskipTests'
                    sh "docker build -t $DOCKERHUB_REPO_BACK:$TAG ."
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('ebanking-frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                    sh "docker build -t $DOCKERHUB_REPO_FRONT:$TAG ."
                }
            }
        }

        stage('Push Images') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USERNAME',
                    passwordVariable: 'PASSWORD'
                )]) {
                    sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
                    sh "docker push $DOCKERHUB_REPO_BACK:$TAG"
                    sh "docker push $DOCKERHUB_REPO_FRONT:$TAG"
                }
            }
        }

        stage('Deploy') {
            steps {
                sh "export TAG=$TAG && docker compose pull"
                sh "export TAG=$TAG && docker compose up -d"
            }
        }
    }
}

