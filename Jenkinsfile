pipeline {
    agent any
    stages {

        stage ('Build') {
            steps {
                sh 'mvn clean install -Dmaven.test.skip=true'
            }
        }

        stage ('Test') {
            steps {
                sh 'mvn test'
            }
        }

        // stage ('Integration test') {
        //     steps {
        //         sh 'mvn test -Dskip.tests=false'
        //     }
        // }

    }
}