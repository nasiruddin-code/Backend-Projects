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
                echo "Checking out branch: ${params.BRANCH_NAME}"
                git branch: "${params.BRANCH_NAME}", url: 'https://github.com/nasiruddin-code/Backend-Projects.git'
                echo "Checkout complete"
            }
        }

        stage('Build with Maven') {
            steps {
                echo "Building project with Maven"
                bat 'mvn clean package -DskipTests'
                echo "Build complete"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image: ${env.DOCKER_IMAGE}"
                bat "docker build -t %DOCKER_IMAGE% ."
                echo "Docker image build complete"
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Logging into Docker Hub and pushing image"
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                        echo Logging in with user %DOCKER_USER%
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker push %DOCKER_IMAGE%
                    '''
                }
                echo "Docker image pushed"
            }
        }

        stage('Test SSH Connection') {
            steps {
                echo "Testing SSH connection to ${env.EC2_HOST}"
                sshagent(credentials: ['jenkins-ssh-key']) {
                    bat '''
                        echo SSH Agent environment variables:
                        set SSH_AUTH_SOCK
                        set SSH_AGENT_PID
                        ssh -o StrictHostKeyChecking=no %EC2_HOST% "echo SSH connection successful"
                    '''
                }
                echo "SSH connection test complete"
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    echo "Starting deployment to EC2"
                    try {
                        sshagent(credentials: ['jenkins-ssh-key']) {
                            bat '''
                                echo SSH Agent environment variables before deploy:
                                set SSH_AUTH_SOCK
                                set SSH_AGENT_PID
                                ssh -o StrictHostKeyChecking=no %EC2_HOST% "
                                    echo Pulling Docker image %DOCKER_IMAGE% &&
                                    docker pull %DOCKER_IMAGE% &&
                                    echo Stopping and removing old container &&
                                    docker rm -f hotelbooking || true &&
                                    echo Running new container &&
                                    docker run -d --name hotelbooking -p 8083:8080 %DOCKER_IMAGE%"
                            '''
                        }
                        echo "Deployment stage completed successfully"
                    } catch (e) {
                        echo "Deployment failed with error: ${e}"
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
                        echo SSH Agent environment variables before tagging backup:
                        set SSH_AUTH_SOCK
                        set SSH_AGENT_PID
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
                        echo SSH Agent environment variables before rollback:
                        set SSH_AUTH_SOCK
                        set SSH_AGENT_PID
                        ssh -o StrictHostKeyChecking=no %EC2_HOST% "
                            docker rm -f hotelbooking || true &&
                            docker run -d --name hotelbooking -p 8083:8080 nasiruddincode/hotelbooking:backup"
                    '''
                }
            }
        }
    }
}
