pipeline {
    agent any

    parameters {
        string(name: 'DEPLOY_VERSION', defaultValue: 'latest', description: 'Version of the app to deploy')
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
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push $DOCKER_IMAGE"
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(credentials: ['jenkins-ssh-key']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no ubuntu@35.173.186.28 '
                            docker pull $DOCKER_IMAGE || exit 1
                            docker stop hotelbooking || true
                            docker rm hotelbooking || true
                            docker run -d --name hotelbooking -p 8080:8080 $DOCKER_IMAGE || exit 1
                        '
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful. Tagging backup image.'
            sshagent(credentials: ['jenkins-ssh-key']) {
                sh '''
                    ssh -o StrictHostKeyChecking=no ubuntu@35.173.186.28 '
                        docker tag $DOCKER_IMAGE nasiruddincode/hotelbooking:backup
                        docker push your-dockerhub-username/hotelbooking:backup
                    '
                '''
            }
        }

        failure {
            echo 'Deployment failed. Rolling back to previous working version.'
            sshagent(credentials: ['jenkins-ssh-key']) {
                sh '''
                    ssh -o StrictHostKeyChecking=no ubuntu@35.173.186.28 '
                        docker stop hotelbooking || true
                        docker rm hotelbooking || true
                        docker run -d --name hotelbooking -p 8080:8080 nasiruddincode/hotelbooking:backup || echo "Rollback failed."
                    '
                '''
            }
        }
    }
}


