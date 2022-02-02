node()  {
  stage('SCM') {
    git branch: 'main', url: 'https://github.com/mabhishek9010/sonar-jenkins.git'
  }
  stage('SonarQube Analysis') {
    def scannerHome = tool 'sonar';
    withSonarQubeEnv() {
      sh "${scannerHome}/bin/sonar-scanner \
      -Dsonar.projectKey= sonar-test \
      -Dsonar.sources=. \
      -Dsonar.host.url=http://20.185.46.73:9000 \
      -Dsonar.login=0543437dd8d1d8e419c4af6b12768fb70119b056"
    }
  }
}
