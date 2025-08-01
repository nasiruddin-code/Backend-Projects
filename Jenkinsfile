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
        EC2_HOST = "ubuntu@13.222.233.139"
        PEM_PATH = "C:\\Users\\Admin\\.ssh\\newec2pemfile.pem"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out branch: ${params.BRANCH_NAME}"
                git branch: "${params.BRANCH_NAME}", url: 'https://github.com/nasiruddin-code/Backend-Projects.git'
            }
        }

        stage('Check PEM File Access') {
            steps {
                echo "Checking PEM file access: ${env.PEM_PATH}"
                bat "type \"${env.PEM_PATH}\""
            }
        }

        stage('SSH Test') {
            steps {
                echo "Testing SSH connection..."
                bat "ssh -i \"${env.PEM_PATH}\" -o StrictHostKeyChecking=no ${env.EC2_HOST} \"echo SSH connection successful\""
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${env.DOCKER_IMAGE} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker push ${env.DOCKER_IMAGE}
                    """
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    echo "Starting deployment to EC2..."
                    deployFailed = false

                    try {
                        bat """
                            ssh -i \"${env.PEM_PATH}\" -o StrictHostKeyChecking=no ${env.EC2_HOST} ^
                            "docker pull ${env.DOCKER_IMAGE} && ^
                             docker rm -f hotelbooking || true && ^
                             docker run -d --name hotelbooking -p 8083:8080 ${env.DOCKER_IMAGE}"
                        """
                        echo " Deployment succeeded."
                    } catch (e) {
                        echo " Deployment failed: ${e}"
                        deployFailed = true
                    }
                }
            }
        }


        stage('Post Deployment Handling') {
            when {
                expression { env.DEPLOY_STATUS == 'SUCCESS' }
            }
            steps {
                echo ' Deployment successful. Tagging backup image.'
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        ssh -i \"${env.PEM_PATH}\" -o StrictHostKeyChecking=no ${env.EC2_HOST} ^
                        "echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin && ^
                         docker tag ${env.DOCKER_IMAGE} nasiruddincode/hotelbooking:backup && ^
                         docker push nasiruddincode/hotelbooking:backup"
                    """
                }
            }
        }
        

        stage('Rollback if Deployment Fails') {
            when {
                expression { return deployFailed }
            }
            steps {
                echo " Rolling back to previous backup image..."
                bat """
                    ssh -i \"${env.PEM_PATH}\" -o StrictHostKeyChecking=no ${env.EC2_HOST} ^
                    "docker rm -f hotelbooking || true && ^
                     docker run -d --name hotelbooking -p 8083:8080 nasiruddincode/hotelbooking:backup"
                """
            }
        }
    }
}
