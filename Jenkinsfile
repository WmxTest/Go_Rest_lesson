pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('setup parameters') {
            steps {
                script {
                    properties([
                        parameters([
                            choice(
                                choices: ['one', 'two'],
                                name: 'amount'
                            )
                        ])
                    ])
                }
            }
        }
        stage('clone code') {
            steps {
                echo 'git'
                git branch: 'ci_test', credentialsId: 'e77e5a08-1b3e-45f7-9b90-ae0973b08ac4', url: 'https://github.com/WmxTest/Go_Rest_lesson.git'
            }
        }
        stage('test') {
            steps {
                script {
                    if ('amount' == 'one') {
                        echo 'test 1'
                        sh 'mvn clean test'
                    } else {
                        echo 'test 2'
                        parallel(
                              a: {
                                sh 'mvn clean test'
                              },
                              b: {
                                sh 'mvn clean test'
                              }
                    }
                }
                echo 'test'
                sh 'mvn clean test'
            }
        }
    }
}