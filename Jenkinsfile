pipeline {
    agent any

    tools {
        nodejs 'NodeJS22'
    }

    environment {
        DOCKERHUB_REPO_FRONT = "oussama0079/ebanking-frontend"
        TAG = "v${BUILD_NUMBER}"
    }

    stages {

        // ================= INSTALL =================

        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }

        // ================= TEST + COVERAGE =================

        stage('Run Tests') {
            steps {
                sh 'npm run test -- --code-coverage --watch=false'
            }
        }

        // ================= SONARQUBE =================

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                        sh "npm run sonar"
                }
            }
        }

        // ================= QUALITY GATE =================

        stage('Quality Gate') {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }

        // ================= BUILD ANGULAR =================

        stage('Build Angular') {
            steps {
                sh 'npm run build'
            }
        }

        // ================= DOCKER BUILD =================

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $DOCKERHUB_REPO_FRONT:$TAG ."
            }
        }

        // ================= PUSH =================

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USERNAME',
                    passwordVariable: 'PASSWORD'
                )]) {
                    sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
                    sh "docker push $DOCKERHUB_REPO_FRONT:$TAG"
                }
            }
        }

        // ================= DEPLOY =================

        stage('Deploy') {
            steps {
                sh """
                    TAG=${TAG} docker compose pull
                    TAG=${TAG} docker compose up -d --force-recreate --remove-orphans
                """
            }
        }
    }
}
