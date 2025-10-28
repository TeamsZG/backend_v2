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
       emailext(
         subject: "✅ [SUCCESS] Build #${BUILD_NUMBER} - ${JOB_NAME}",
         body: """\
         <h2>Build réussi 🎉</h2>
         <p><b>Projet :</b> ${JOB_NAME}</p>
         <p><b>Numéro du build :</b> ${BUILD_NUMBER}</p>
         <p><b>Statut :</b> <span style='color:green;'>Succès ✅</span></p>
         <p><b>URL Jenkins :</b> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
         <hr><i>Message automatique envoyé par Jenkins.</i>
         """,
         to: 'chadi-elchami@outlook.com',
         from: 'chadi-elchami@outlook.com',
         mimeType: 'text/html'
       )
     }

     failure {
       emailext(
         subject: "❌ [FAILURE] Build #${BUILD_NUMBER} - ${JOB_NAME}",
         body: """\
         <h2>Build échoué ❌</h2>
         <p><b>Projet :</b> ${JOB_NAME}</p>
         <p><b>Numéro du build :</b> ${BUILD_NUMBER}</p>
         <p><b>Statut :</b> <span style='color:red;'>Échec</span></p>
         <p><b>URL Jenkins :</b> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
         <hr><i>Message automatique envoyé par Jenkins.</i>
         """,
         to: 'chadi-elchami@outlook.com',
         from: 'chadi-elchami@outlook.com',
         mimeType: 'text/html'
       )
     }
   }
 }
