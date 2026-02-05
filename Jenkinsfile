pipeline {
    agent any

    tools {
        jdk 'JDK17'             // Java pour backend
        nodejs 'NodeJS22'       // Node pour frontend
        maven 'Maven'          // Maven pour backend
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Frontend - Install') {
            steps {
                dir('ebanking-frontend') {
                    sh 'npm install'
                }
            }
        }

        stage('Frontend - Build') {
            steps {
                dir('ebanking-frontend') {
                    sh 'npm install -g @angular/cli'
                    sh 'npm run build'
                }
            }
        }

        stage('Backend - Build') {
            steps {
                dir('ebanking-backend') {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Backend - Run Tests') {
            steps {
                dir('ebanking-backend') {
                    sh 'mvn test'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Frontend + Backend build réussi'
        }
        failure {
            echo '❌ Erreur lors du build'
        }
    }
}
