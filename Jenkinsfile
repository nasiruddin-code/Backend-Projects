pipeline {
    agent any

    parameters {
        string(name: 'DEPLOY_VERSION', defaultValue: 'v1.0.0', description: 'Version of the app to deploy')
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Git branch to build from')
        choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: 'Target environment')
    }

    environment {
        DOCKER_IMAGE = "nasiruddincode/hotelbooking:${params.DEPLOY_VERSION}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: params.BRANCH_NAME, url: 'https://github.com/nasiruddin-code/Backend-Projects.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker push %DOCKER_IMAGE%
                    '''
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    def deployCommand = '''
                        docker pull nasiruddincode/hotelbooking:v1.0.0 || exit 1
                        docker stop hotelbooking || exit /b 0
                        docker rm hotelbooking || exit /b 0
                        docker run -d --name hotelbooking -p 8083:8080 nasiruddincode/hotelbooking:v1.0.0
                    '''
                    bat """
                        ssh -o StrictHostKeyChecking=no -i C:\\Users\\Admin\\Downloads\\github-actions.pem ubuntu@35.173.186.28 "${deployCommand}"

                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful. Tagging backup image.'
            sshagent(credentials: ['jenkins-ssh-key']) {
                bat '''
                    ssh -o StrictHostKeyChecking=no ubuntu@35.173.186.28 '
                        docker tag $DOCKER_IMAGE nasiruddincode/hotelbooking:backup
                        docker push nasiruddincode/hotelbooking:backup
                    '
                '''
            }
        }

        failure {
            echo 'Deployment failed. Rolling back to previous working version.'
            sshagent(credentials: ['jenkins-ssh-key']) {
                bat '''
                    ssh -o StrictHostKeyChecking=no ubuntu@35.173.186.28 '
                        docker stop hotelbooking || exit /b 0
                        docker rm hotelbooking || exit /b 0
                        docker run -d --name hotelbooking -p 8083:8080 nasiruddincode/hotelbooking:backup || echo "Rollback failed."
                    '
                '''
            }
        }
    }
}


