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
        sh 'echo "Branch: $(git rev-parse --abbrev-ref HEAD) | Commit: $(git rev-parse --short HEAD)"'
      }
    }

    stage('Build & Package') {
      steps {
        echo "=== Étape 3.1 : Compilation et packaging ==="
        sh 'mvn -B -DskipTests=true clean package'
      }
    }

stage('Test & Coverage') {
  steps {
    sh 'mvn -B clean verify'
  }
  post {
    always {
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
      publishCoverage(
        adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')],
        sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
      )
      archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
    }
  }
}


    stage('Docker Build (optionnel)') {
      when { expression { return params.BUILD_DOCKER } }
      steps {
        echo "=== Étape 3.2 : Build Docker (facultatif) ==="
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
      withCredentials([string(credentialsId: 'DISCORD_WEBHOOK', variable: 'WEBHOOK')]) {
        sh '''
          curl -H "Content-Type: application/json" \
               -X POST \
               -d "{
                    \\"username\\": \\"Jenkins\\",
                    \\"embeds\\": [{
                      \\"title\\": \\"✅ Build #${BUILD_NUMBER} réussi !\\",
                      \\"description\\": \\"Projet **${JOB_NAME}**\\nStatut: ✅ **SUCCÈS**\\n[Voir les détails du build](${BUILD_URL})\\",
                      \\"color\\": 3066993
                    }]
                  }" \
               "$WEBHOOK"
        '''
      }
    }
  }

  failure {
    script {
      withCredentials([string(credentialsId: 'DISCORD_WEBHOOK', variable: 'WEBHOOK')]) {
        sh '''
          curl -H "Content-Type: application/json" \
               -X POST \
               -d "{
                    \\"username\\": \\"Jenkins\\",
                    \\"embeds\\": [{
                      \\"title\\": \\"❌ Build #${BUILD_NUMBER} échoué\\",
                      \\"description\\": \\"Projet **${JOB_NAME}**\\nStatut: ❌ **ÉCHEC**\\n[Voir les détails du build](${BUILD_URL})\\",
                      \\"color\\": 15158332
                    }]
                  }" \
               "$WEBHOOK"
        '''
      }
    }
  }
}