pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('clone code') {
            steps {
                echo 'git'
                git branch: 'ci_test', credentialsId: 'e77e5a08-1b3e-45f7-9b90-ae0973b08ac4', url: 'https://github.com/WmxTest/Go_Rest_lesson.git'
            }
        }
        stage('build code') {
            steps {
                echo 'test'
                sh 'mvn clean test'
            }
        }
    }
}