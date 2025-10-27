pipeline {
  agent any

  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '20'))
    skipDefaultCheckout(true)
  }

  parameters {
    booleanParam(name: 'BUILD_DOCKER', defaultValue: false, description: 'Construire l’image Docker (optionnel)')
  }

  environment {
    ARTIFACTS        = 'target/*.jar, target/*.war'
    JUNIT_SUREFIRE   = 'target/surefire-reports/*.xml'
    JUNIT_FAILSAFE   = 'target/failsafe-reports/*.xml'
    JACOCO_XML       = 'target/site/jacoco/jacoco.xml'
    DOCKER_IMAGE     = "monapp:${BUILD_NUMBER}"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
        sh 'echo "Branch: $(git rev-parse --abbrev-ref HEAD)  |  Commit: $(git rev-parse --short HEAD)"'
      }
    }

    stage('Build & Package') {
      steps {
        sh 'mvn -B -DskipTests=false clean package'
      }
      post {
        always {
          archiveArtifacts artifacts: "${ARTIFACTS}", fingerprint: true, onlyIfSuccessful: false
        }
      }
    }

    stage('Test & Coverage') {
      steps {
        sh 'mvn -B verify'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: "${JUNIT_SUREFIRE}, ${JUNIT_FAILSAFE}"
          recordCoverage(
            tools: [jacocoAdapter("${JACOCO_XML}")],
            sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
          )
          archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: false, onlyIfSuccessful: false
        }
      }
    }

    stage('Docker Build (optionnel)') {
      when { expression { return params.BUILD_DOCKER } }
      steps {
        sh '''
          docker build -t "$DOCKER_IMAGE" .
          docker image ls "$DOCKER_IMAGE"
        '''
      }
    }
  }

  post {
    success {
      script {
        echo "Build #${BUILD_NUMBER} OK"
        // Notif Discord si webhook configuré (Credentials ID = DISCORD_WEBHOOK)
        withCredentials([string(credentialsId: 'DISCORD_WEBHOOK', variable: 'WEBHOOK')]) {
          sh '''
            curl -s -H "Content-Type: application/json" \
                 -d "{\"content\":\" Jenkins: Build #${BUILD_NUMBER} réussi sur ${JOB_NAME} (${BUILD_URL})\"}" \
                 "$WEBHOOK" || true
          '''
        }
      }
    }
    failure {
      script {
        echo " Build #${BUILD_NUMBER} ÉCHEC"
        withCredentials([string(credentialsId: 'DISCORD_WEBHOOK', variable: 'WEBHOOK')]) {
          sh '''
            curl -s -H "Content-Type: application/json" \
                 -d "{\"content\":\" Jenkins: Build #${BUILD_NUMBER} ÉCHEC sur ${JOB_NAME} (${BUILD_URL})\"}" \
                 "$WEBHOOK" || true
          '''
        }
      }
    }
  }
}
