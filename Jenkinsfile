pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '30'))
     }
    agent any

    parameters {
        booleanParam(defaultValue: false, description: 'Release and publish', name: "releaseAndPublish")
    }

    tools {
        jdk 'jdk8'
        maven 'default'
    }

    stages {

        /*******************************************************/
        /*   Maven install including tests and Sonar analysis  */
        /*******************************************************/

        stage('[Build] - Clean workspace') {
          steps {

             sh "mvn clean"

          }
        }

        stage('[Publishing] Snapshot') {

           when {
               expression {
                   return !params.releaseAndPublish
               }
           }

           steps {
                sh "mvn -DskipTests deploy"
           }

        }

    }
}
