pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('checkout') {
            steps {
                echo 'checkout'
                git branch: 'ci_test', credentialsId: 'e77e5a08-1b3e-45f7-9b90-ae0973b08ac4', url: 'https://github.com/WmxTest/Go_Rest_lesson.git'
            }
        }
        stage('Test') {
            steps {
                echo 'Test'
                sh 'mvn clean test'
            }
        }
    }
}