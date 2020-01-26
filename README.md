Assignment Requirements
=========================

-  Create the project using Maven/Gradle
-  create a branch from InnerSource repository and commit your App into that branch.
-  Implement the main logic of your selected topics with all the needed services, UIs, DTO, etc.
-  Your app should use Spring framework
-  All services must have 70% to 80% test coverage
-  Your app should have both Unit and Integration tests
-  you should create Jenkins jobs to build and test the application.
-  All code must comply with the Java naming conventions and best practices(use SonalLint/SonaQube )
-  At the presentation session you should be able to demo at least 2 working functions on your app
-  You should provide a presentation showing a Sonar report on the test coverage and code quality in your app and explaining the idea you&#39;ve picked and implemented
-  You should have at least 3 commits per preson on InnerSource repository for  your app.

MAC setup 
====================
brew install jenkins-lts
brew services start jenkins-lts
brew services stop jenkins-lts
/usr/local/Cellar/jenkins-lts/2.121.2/homebrew.mxcl.jenkins-lts.plist   === to change port
/Users/user/.jenkins/


Sonar command for report -- https://sonarcloud.io/projects
===============================
mvn sonar:sonar \
  -Dsonar.projectKey=serawwerastat.Application-Development-Fundamentals \
  -Dsonar.organization=serawwerastat \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=1880ef092dc2da716c9c79d2b35114d79b3ba112


Maven job
===============================

cd /Users/artur.filatov/Documents/JAVA_Study/Jenkins-path/
export MAVEN_HOME=/usr/local/Cellar/maven/3.6.1/libexec
export PATH=$PATH:$MAVEN_HOME/bin
mvn --version
mvn clean
mvn package


