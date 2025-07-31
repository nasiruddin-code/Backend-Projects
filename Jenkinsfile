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
        SSH_KEY_PATH = "C:\\Jenkins\\ssh\\github-actions.pem"
        EC2_HOST = "ubuntu@35.173.186.28"
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
                bat """
                    ssh -o StrictHostKeyChecking=no -i "%SSH_KEY_PATH%" %SSH_USER%@%SSH_HOST% "echo SSH connection successful"
                """
            }
        }

        stage('Deploy to EC2') {
            steps {
                bat """
                    ssh -o StrictHostKeyChecking=no -i "%SSH_KEY_PATH%" %EC2_HOST% ^
                    "docker pull %DOCKER_IMAGE% && \
                    docker stop hotelbooking || true && \
                    docker rm hotelbooking || true && \
                    docker run -d --name hotelbooking -p 8083:8080 %DOCKER_IMAGE%"
                """
            }
        }
    }

    post {
        success {
            echo '✅ Deployment successful. Tagging backup image.'
            bat """
                ssh -o StrictHostKeyChecking=no -i "%SSH_KEY_PATH%" %EC2_HOST% ^
                "docker tag %DOCKER_IMAGE% nasiruddincode/hotelbooking:backup && \
                docker push nasiruddincode/hotelbooking:backup"
            """
        }

        failure {
            echo '⚠️ Deployment failed. Rolling back to previous working version.'
            bat """
                ssh -o StrictHostKeyChecking=no -i "%SSH_KEY_PATH%" %EC2_HOST% ^
                "docker stop hotelbooking || true && \
                docker rm hotelbooking || true && \
                docker run -d --name hotelbooking -p 8083:8080 nasiruddincode/hotelbooking:backup"
            """
        }
    }
}
