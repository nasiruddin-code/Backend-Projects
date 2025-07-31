pipeline {
    agent any

    parameters {
        string(name: 'DEPLOY_VERSION', defaultValue: 'v1.0.0', description: 'Version of the app to deploy')
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Git branch to build from')
        choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: 'Target environment')
    }

    environment {
        DEPLOY_VERSION = "${params.DEPLOY_VERSION}"
        DOCKER_IMAGE = "nasiruddincode/hotelbooking:${params.DEPLOY_VERSION}"
        EC2_HOST = "ubuntu@52.207.126.136"
        DEPLOY_STATUS = "SUCCESS"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${params.BRANCH_NAME}", url: 'https://github.com/nasiruddin-code/Backend-Projects.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE% ."
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

        stage('Test SSH Connection') {
            steps {
                sshagent(credentials: ['jenkins-ssh-key']) {
                    bat 'ssh -o StrictHostKeyChecking=no %EC2_HOST% "echo SSH connection successful"'
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    try {
                        sshagent(credentials: ['jenkins-ssh-key']) {
                            bat '''
                                ssh -o StrictHostKeyChecking=no %EC2_HOST% "
                                    docker pull %DOCKER_IMAGE% &&
                                    docker rm -f hotelbooking || true &&
                                    docker run -d --name hotelbooking -p 8083:8080 %DOCKER_IMAGE%"
                            '''
                        }
                    } catch (e) {
                        env.DEPLOY_STATUS = "FAILED"
                        error("Deployment failed. Marking for rollback.")
                    }
                }
            }
        }

        stage('Post Deployment Handling') {
            when {
                expression { env.DEPLOY_STATUS == 'SUCCESS' }
            }
            steps {
                echo '✅ Deployment successful. Tagging backup image.'
                sshagent(credentials: ['jenkins-ssh-key']) {
                    bat '''
                        ssh -o StrictHostKeyChecking=no %EC2_HOST% "
                            docker tag %DOCKER_IMAGE% nasiruddincode/hotelbooking:backup &&
                            docker push nasiruddincode/hotelbooking:backup"
                    '''
                }
            }
        }

        stage('Rollback if Deployment Fails') {
            when {
                expression { env.DEPLOY_STATUS == 'FAILED' }
            }
            steps {
                echo '⚠️ Rolling back to previous working version.'
                sshagent(credentials: ['jenkins-ssh-key']) {
                    bat '''
                        ssh -o StrictHostKeyChecking=no %EC2_HOST% "
                            docker rm -f hotelbooking || true &&
                            docker run -d --name hotelbooking -p 8083:8080 nasiruddincode/hotelbooking:backup"
                    '''
                }
            }
        }
    }
}
