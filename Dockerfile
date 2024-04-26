FROM jenkins/jenkins:lts
COPY my_marvin.yml /var/jenkins_home/my_marvin.yml
COPY .env /var/jenkins_home/.env
COPY job_dsl.groovy /var/jenkins_home/job_dsl.groovy
RUN jenkins-plugin-cli --plugins "cloudbees-folder:latest configuration-as-code:latest credentials:latest github:latest instance-identity:latest job-dsl:latest script-security:latest structs:latest role-strategy:latest ws-cleanup:latest"
ENV CASC_JENKINS_CONFIG /var/jenkins_home/my_marvin.yml
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"