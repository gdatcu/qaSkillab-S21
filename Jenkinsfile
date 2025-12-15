pipeline {
    agent any

    tools {
        maven 'Maven 3.8.9'
        jdk 'JDK 21' // <--- AICI trebuie să fie numele pus la pasul 1 (Tools)
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/gdatcu/qaSkillab-S21.git'
            }
        }

        stage('Build & Test') {
            steps {
                script {
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
            // Această comandă va funcționa doar după instalarea plugin-ului
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'target/allure-results']]

            archiveArtifacts artifacts: 'logs/**/*.log', allowEmptyArchive: true
        }
    }
}