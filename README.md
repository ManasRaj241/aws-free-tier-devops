# AWS Free Tier DevOps Project

A complete **Infrastructure as Code (IaC)** and **CI/CD automation** project demonstrating enterprise-grade AWS deployment practices—entirely within the AWS Free Tier with **zero cost**.

## 🎯 Project Overview

This project showcases:
- **Infrastructure as Code** using CloudFormation
- **Automated CI/CD Pipeline** with AWS CodePipeline and CodeBuild
- **Java Application Deployment** on EC2
- **GitHub Integration** for automated deployments on code push
- **Complete Free Tier Compliance** (no paid services used)

## 🏗️ Architecture

```
GitHub Repository
       ↓
AWS CodePipeline (triggered by Git push)
       ↓
AWS CodeBuild (compiles & packages Java app)
       ↓
AWS CloudFormation (provisions/updates infrastructure)
       ↓
Amazon EC2 t2.micro (runs Java web application)
       ↓
Amazon S3 (stores build artifacts)
```

## 📦 Technologies Used

- **Java 11** - Application runtime
- **Spring Boot** - Web framework
- **Maven** - Build tool
- **AWS CodePipeline** - CI/CD orchestration
- **AWS CodeBuild** - Build automation
- **AWS CloudFormation** - Infrastructure as Code
- **Amazon EC2 t2.micro** - Application server
- **Amazon S3** - Artifact storage
- **AWS IAM** - Access control
- **Amazon CloudWatch** - Monitoring and logs

## 🚀 Quick Start

### Prerequisites
- AWS Free Tier account (us-east-1 region)
- GitHub account
- Local machine with Git, Java 11+, and Maven

### Setup Instructions

1. **Clone this repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/aws-free-tier-devops.git
   cd aws-free-tier-devops
   ```

2. **Create AWS IAM Roles** (see Phase 1 documentation)
   - CodePipelineServiceRole
   - CodeBuildServiceRole
   - EC2InstanceRole

3. **Create EC2 Key Pair**
   ```bash
   # AWS Console → EC2 → Key Pairs → Create
   # Save as: aws-devops-key.pem
   chmod 600 ~/Downloads/aws-devops-key.pem
   ```

4. **Deploy CloudFormation Stack**
   ```bash
   aws cloudformation create-stack \
     --stack-name aws-devops-stack \
     --template-body file://infrastructure/infrastructure.yaml \
     --parameters ParameterKey=KeyPairName,ParameterValue=aws-devops-key \
     --region us-east-1
   ```

5. **Set up CodePipeline** (see Phase 5 documentation)
   - Connect GitHub repository
   - Configure CodeBuild project
   - Link CloudFormation deployment stage

6. **Push code to trigger pipeline**
   ```bash
   git add .
   git commit -m "Initial commit"
   git push origin main
   ```

## 📁 Project Structure

```
aws-free-tier-devops/
├── app/
│   ├── pom.xml                          # Maven configuration
│   └── src/
│       ├── main/
│       │   ├── java/com/awsdevops/
│       │   │   └── Application.java    # Spring Boot app
│       │   └── resources/
│       │       └── application.properties
│       └── test/
├── infrastructure/
│   └── infrastructure.yaml              # CloudFormation template
├── scripts/
│   └── deploy.sh                        # Deployment script
├── buildspec.yml                        # CodeBuild configuration
└── README.md                            # This file
```

## 🔗 Application Features

The deployed Java application displays:
- ✅ Deployment status badge
- 📅 Current timestamp of deployment
- 🖥️ EC2 instance details (hostname, IP, instance ID)
- 🛠️ Technology stack used
- 💰 "Deployed on AWS Free Tier" badge

**Access the app:** `http://<EC2_PUBLIC_IP>:8080`

## 💰 Free Tier Compliance

| Service | Free Tier Limit | Usage | Status |
|---------|-----------------|-------|--------|
| EC2 t2.micro | 750 hrs/month | 1 instance | ✅ Safe |
| S3 | 5 GB storage | Build artifacts | ✅ Safe |
| CodePipeline | 1 pipeline | 1 active pipeline | ✅ Safe |
| CodeBuild | 100 build min/month | ~5-10 min/build | ✅ Safe |
| CloudFormation | Free | Infrastructure provisioning | ✅ Free |
| CloudWatch | 5 GB logs/month | Application logs | ✅ Safe |
| IAM | Free | Access management | ✅ Free |

## 🧹 Cleanup Instructions

**To avoid any charges, follow these steps:**

```bash
# 1. Delete CloudFormation Stack
aws cloudformation delete-stack \
  --stack-name aws-devops-stack \
  --region us-east-1

# 2. Empty and delete S3 bucket
aws s3 rm s3://aws-devops-artifacts-ACCOUNT_ID-us-east-1 --recursive
aws s3 rb s3://aws-devops-artifacts-ACCOUNT_ID-us-east-1

# 3. Delete CodeBuild project (via console or CLI)
aws codebuild delete-project --name aws-devops-codebuild

# 4. Delete CodePipeline (via console or CLI)
aws codepipeline delete-pipeline --name aws-devops-pipeline

# 5. Delete IAM roles (if created for this project)
aws iam delete-role-policy --role-name CodePipelineServiceRole --policy-name inline-policy
aws iam delete-role --role-name CodePipelineServiceRole
# Repeat for other roles...

# 6. Verify EC2 instance is terminated
aws ec2 describe-instances --region us-east-1
```

## 📚 Learning Outcomes

This project demonstrates:
- ✅ Infrastructure as Code best practices
- ✅ CI/CD pipeline design and implementation
- ✅ AWS service integration and orchestration
- ✅ Security through IAM roles and least privilege
- ✅ Cost optimization (Free Tier usage)
- ✅ Application deployment automation

## 🎓 Resume Bullet Points

```
• Designed and implemented a complete CI/CD pipeline on AWS using 
  CodePipeline, CodeBuild, and CloudFormation (100% Free Tier)

• Automated Java application deployment with GitHub integration 
  and Infrastructure as Code principles

• Implemented least-privilege IAM roles and security groups 
  for secure AWS infrastructure

• Demonstrated proficiency in AWS DevOps services: CodePipeline, 
  CodeBuild, CloudFormation, EC2, S3, and IAM
```

## 🔐 Security Considerations

- ✅ IAM roles follow least privilege principle
- ✅ Security groups restrict traffic to necessary ports only (22, 80, 8080)
- ✅ S3 bucket has public access blocked
- ✅ EC2 key pair secured locally with 600 permissions
- ⚠️ Note: SSH access (port 22) is open to 0.0.0.0/0 for demo purposes—restrict in production

## 🤝 Contributing

Feel free to fork and enhance this project!

## 📝 License

This project is open source and available under the MIT License.

## ❓ Troubleshooting

### Application not accessible
- Verify Security Group allows port 8080
- Check EC2 instance is running
- Review CloudWatch logs

### CodeBuild failing
- Check buildspec.yml syntax
- Verify IAM permissions for CodeBuild role
- Review CodeBuild logs in console

### CodePipeline stuck
- Verify GitHub personal access token is valid
- Check CloudFormation stack events for errors
- Review CodePipeline execution details

## 📞 Support

For issues or questions, refer to:
- [AWS Documentation](https://docs.aws.amazon.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [CloudFormation User Guide](https://docs.aws.amazon.com/cloudformation/)