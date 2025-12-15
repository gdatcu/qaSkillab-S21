pipeline {
    agent any

    tools {
        // Folosim numele definite in 'Global Tool Configuration'
        maven 'Maven 3.8.9'
        jdk 'JDK 21' // Rezolvă eroarea 'invalid target release: 21'
    }

    stages {
        stage('Checkout') {
            steps {
                // Înlocuiește cu URL-ul repo-ului tău
                // Pentru branch, pune 'main' sau 'master' exact cum e pe GitHub
                git branch: 'main', url: 'https://github.com/gdatcu/qaSkillab-S21.git'
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    // Rulează testele și ignoră erorile pentru a permite generarea raportului
                    if (isUnix()) {
                        sh 'mvn clean test -Dmaven.test.failure.ignore=true'
                    } else {
                        bat 'mvn clean test -Dmaven.test.failure.ignore=true'
                    }
                }
            }
        }
    }

    post {
        always {
            // Generare Raport Allure
            // commandline: Trebuie să fie IDENTIC cu numele din Global Tool Configuration
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'target/allure-results']],
                   commandline: 'Allure 2.36.0'

            // Arhivare Log-uri
            archiveArtifacts artifacts: 'logs/**/*.log', allowEmptyArchive: true
        }
        failure {
            echo 'Build Failed. Verificați consola pentru detalii.'
        }
    }
}
