pipeline {
    agent any // Rulează pe orice agent disponibil

    tools {
        // Numele configurate la Pasul 4 de mai sus
        maven 'Maven 3.8.9'
    }

    stages {
        stage('Checkout') {
            steps {
                // Descarcă codul din Git (înlocuiește cu URL-ul tău)
                // Dacă e repo privat, va trebui să configurezi Credentials în Jenkins
                git branch: 'main', url: 'https://github.com/gdatcu/qaSkillab-S21.git'
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    // Rulează testele și ignoră erorile temporar pentru a ajunge la raportare
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
            // 1. Generare Raport Allure
            // Necesită plugin-ul Allure instalat și configurat în Tools
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'target/allure-results']]

            // 2. Arhivare Log-uri Log4j
            // Păstrează fișierele .log ca artefacte descărcabile
            archiveArtifacts artifacts: 'logs/**/*.log', allowEmptyArchive: true
        }
        failure {
            echo 'ATENȚIE: Testele hapifyMe au picat!'
        }
    }
}
