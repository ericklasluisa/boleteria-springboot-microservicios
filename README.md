# Boleteria Spring Boot Microservicios

A comprehensive microservices-based application built with Spring Boot, designed to manage publications, authors, articles, and books with a robust distributed architecture.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Microservices](#microservices)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Local Development](#local-development)
- [Kubernetes Deployment](#kubernetes-deployment)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [AWS Deployment](#aws-deployment)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## 🎯 Overview

This project is a distributed microservices application that demonstrates modern cloud-native architecture patterns. It manages a publication system where users can create and manage authors, books, and articles with real-time notifications and catalog synchronization.

### Key Features

- **Microservices Architecture**: Modular, independently deployable services
- **Service Discovery**: Netflix Eureka for dynamic service registration
- **API Gateway**: Centralized routing and load balancing
- **Message Queue**: RabbitMQ for asynchronous communication
- **Distributed Database**: CockroachDB cluster for high availability
- **Container Orchestration**: Kubernetes deployment configurations
- **Security**: JWT-based authentication and authorization
- **Load Testing**: Locust for performance testing
- **Cloud Ready**: Deployed and tested on AWS

## 🏗 Architecture

```
┌─────────────────┐
│   API Gateway   │ (Port 8000)
│   (Spring Cloud)│
└────────┬────────┘
         │
    ┌────┴─────────────────────────┐
    │    Eureka Server (8761)      │
    │    Service Discovery         │
    └────┬─────────────────────────┘
         │
    ┌────┴────────────────────────────────────┐
    │                                          │
┌───▼────────┐  ┌──────────┐  ┌─────────────┐│
│Publicaciones│  │ Catalogo │  │Notificaciones│
│   Service  │  │ Service  │  │   Service    │
└────┬───────┘  └─────┬────┘  └──────┬──────┘
     │                │                │
     └────────┬───────┴───────┬────────┘
              │               │
     ┌────────▼────┐    ┌────▼─────────┐
     │  RabbitMQ   │    │ CockroachDB  │
     │   Queue     │    │   Cluster    │
     └─────────────┘    └──────────────┘
              │
     ┌────────▼────────┐
     │ Sincronizacion  │
     │    Service      │
     └─────────────────┘
```

## 🛠 Technologies

### Core Technologies

- **Java 17**: Programming language
- **Spring Boot 3.x**: Application framework
- **Spring Cloud**: Microservices framework
  - Spring Cloud Gateway
  - Netflix Eureka
- **Maven**: Build and dependency management

### Infrastructure

- **Docker**: Containerization
- **Kubernetes**: Container orchestration
- **CockroachDB**: Distributed SQL database (PostgreSQL-compatible)
- **RabbitMQ**: Message broker
- **Locust**: Load testing framework

### Security & Observability

- **JWT (JSON Web Tokens)**: Authentication
- **Spring Security**: Authorization
- **Spring Boot Actuator**: Health checks and metrics

### Cloud Platform

- **AWS**: Cloud deployment platform
  - EC2 for compute
  - EKS for Kubernetes
  - RDS/CockroachDB for database

## 🔧 Microservices

### 1. MS Eureka Server
- **Port**: 8761
- **Purpose**: Service discovery and registration
- **Technology**: Netflix Eureka Server
- **Description**: Central registry where all microservices register and discover each other

### 2. MS API Gateway
- **Port**: 8000
- **Purpose**: Single entry point for all client requests
- **Technology**: Spring Cloud Gateway
- **Features**:
  - Dynamic routing based on Eureka service discovery
  - Load balancing
  - Request/response filtering
  - JWT token validation

### 3. MS Auth (Authentication Service)
- **Port**: Dynamic (registered with Eureka)
- **Database**: CockroachDB (auth_db)
- **Purpose**: User authentication and authorization
- **Features**:
  - JWT token generation
  - User management
  - Role-based access control (RBAC)
  - OAuth2 security

### 4. Mis Publicaciones (Publications Service)
- **Port**: Dynamic (registered with Eureka)
- **Database**: CockroachDB (publicaciones_db)
- **Purpose**: Manage publications, authors, books, and articles
- **Features**:
  - Author CRUD operations
  - Book management
  - Article management
  - Publication listing

### 5. MS Catalogo (Catalog Service)
- **Port**: Dynamic (registered with Eureka)
- **Database**: CockroachDB (catalogo_db)
- **Purpose**: Synchronized catalog of all publications
- **Features**:
  - Read-only catalog view
  - Receives updates via RabbitMQ
  - Optimized for search and retrieval

### 6. MS Notificaciones (Notifications Service)
- **Port**: Dynamic (registered with Eureka)
- **Database**: CockroachDB (notificaciones_db)
- **Purpose**: Handle notifications and alerts
- **Features**:
  - Notification storage
  - Event-driven notifications
  - RabbitMQ consumer

### 7. Sincronizacion (Synchronization Service)
- **Port**: Dynamic (registered with Eureka)
- **Purpose**: Scheduled synchronization between services
- **Features**:
  - Periodic data synchronization
  - RabbitMQ publisher
  - Event scheduling

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.8+**
- **Docker** and **Docker Compose**
- **Kubernetes** (Minikube or Docker Desktop with K8s)
- **kubectl** CLI tool
- **Git**
- **Python 3.x** (for Locust testing)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ericklasluisa/boleteria-springboot-microservicios.git
cd boleteria-springboot-microservicios
```

### 2. Build All Microservices

```bash
# Build each microservice
cd ms-eureka-server && mvn clean install -DskipTests && cd ..
cd ms-api-gateway && mvn clean install -DskipTests && cd ..
cd ms-auth && mvn clean install -DskipTests && cd ..
cd mis-publicaciones && mvn clean install -DskipTests && cd ..
cd ms-catalogo && mvn clean install -DskipTests && cd ..
cd ms-notificaciones && mvn clean install -DskipTests && cd ..
cd sincronizacion && mvn clean install -DskipTests && cd ..
```

Or use a script:

```bash
#!/bin/bash
for service in ms-eureka-server ms-api-gateway ms-auth mis-publicaciones ms-catalogo ms-notificaciones sincronizacion; do
    echo "Building $service..."
    cd $service && mvn clean install -DskipTests && cd ..
done
```

## 💻 Local Development

### Option 1: Run Locally with Docker Compose

#### Step 1: Start CockroachDB Cluster

```bash
cd bdd
docker-compose up -d
```

This starts a 3-node CockroachDB cluster:
- Node 1: localhost:26257 (SQL), localhost:8080 (UI)
- Node 2: localhost:26258 (SQL), localhost:8081 (UI)
- Node 3: localhost:26259 (SQL), localhost:8082 (UI)

#### Step 2: Initialize Databases

```bash
# Create databases
docker exec -it crdb-node1 ./cockroach sql --insecure -e "CREATE DATABASE IF NOT EXISTS publicaciones_db;"
docker exec -it crdb-node2 ./cockroach sql --insecure -e "CREATE DATABASE IF NOT EXISTS notificaciones_db;"
docker exec -it crdb-node3 ./cockroach sql --insecure -e "CREATE DATABASE IF NOT EXISTS catalogo_db;"
docker exec -it crdb-node3 ./cockroach sql --insecure -e "CREATE DATABASE IF NOT EXISTS auth_db;"
```

#### Step 3: Start RabbitMQ

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=admin \
  -e RABBITMQ_DEFAULT_PASS=admin \
  rabbitmq:3-management
```

Access RabbitMQ Management UI: http://localhost:15672 (admin/admin)

#### Step 4: Start Microservices in Order

```bash
# 1. Start Eureka Server
cd ms-eureka-server
mvn spring-boot:run

# 2. Start API Gateway (in new terminal)
cd ms-api-gateway
mvn spring-boot:run

# 3. Start Auth Service (in new terminal)
cd ms-auth
mvn spring-boot:run

# 4. Start Publications Service (in new terminal)
cd mis-publicaciones
mvn spring-boot:run

# 5. Start Catalog Service (in new terminal)
cd ms-catalogo
mvn spring-boot:run

# 6. Start Notifications Service (in new terminal)
cd ms-notificaciones
mvn spring-boot:run

# 7. Start Synchronization Service (in new terminal)
cd sincronizacion
mvn spring-boot:run
```

#### Step 5: Verify Services

- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8000
- RabbitMQ Management: http://localhost:15672
- CockroachDB UI: http://localhost:8080

## ☸️ Kubernetes Deployment

### Prerequisites

Ensure you have a running Kubernetes cluster (Minikube, Docker Desktop, or cloud provider).

### Step 1: Build Docker Images

```bash
# Build Docker images for each service
docker build -t ms-eureka-server:latest ./ms-eureka-server
docker build -t ms-api-gateway:latest ./ms-api-gateway
docker build -t ms-auth:latest ./ms-auth
docker build -t mis-publicaciones:latest ./mis-publicaciones
docker build -t ms-catalogo:latest ./ms-catalogo
docker build -t ms-notificaciones:latest ./ms-notificaciones
docker build -t sincronizacion:latest ./sincronizacion
```

### Step 2: Create Namespace

```bash
kubectl apply -f kubernetes/namespace.yml
```

### Step 3: Deploy Infrastructure Components

```bash
# Deploy ConfigMap
kubectl apply -f kubernetes/configmap.yml

# Deploy CockroachDB Cluster
kubectl apply -f kubernetes/cockroach-service.yml

# Wait for CockroachDB to be ready
kubectl wait --for=condition=ready pod -l app=cockroachdb -n microservicios --timeout=300s

# Initialize CockroachDB cluster
kubectl apply -f kubernetes/cockroach-job.yml

# Deploy RabbitMQ
kubectl apply -f kubernetes/rabbitmq.yml
```

### Step 4: Deploy Microservices

```bash
# Deploy Eureka Server
kubectl apply -f kubernetes/eureka.yml

# Wait for Eureka to be ready
sleep 30

# Deploy API Gateway
kubectl apply -f kubernetes/gateway.yml

# Deploy Auth Service
kubectl apply -f kubernetes/auth.yml

# Deploy Application Services
kubectl apply -f kubernetes/publicaciones.yml
kubectl apply -f kubernetes/catalogo.yml
kubectl apply -f kubernetes/notificaciones.yml
kubectl apply -f kubernetes/sincronizacion.yml
```

### Step 5: Deploy Ingress (Optional)

```bash
# For Minikube, enable ingress
minikube addons enable ingress

# Apply ingress configuration
kubectl apply -f kubernetes/ingress.yml

# Add to /etc/hosts
echo "$(minikube ip) app-publicaciones.local" | sudo tee -a /etc/hosts
```

### Step 6: Verify Deployment

```bash
# Check all pods
kubectl get pods -n microservicios

# Check services
kubectl get svc -n microservicios

# Access API Gateway (NodePort)
# Gateway is available at: http://<node-ip>:30081
```

### Access Services

```bash
# Get Minikube IP
minikube ip

# API Gateway: http://<minikube-ip>:30081
# Eureka: http://<minikube-ip>:30081/eureka
```

## 📚 API Documentation

### Authentication

#### Login

```bash
POST http://localhost:8000/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Default Credentials:**
- Admin: username: `admin`, password: `admin123`
- User: username: `user`, password: `user123`

### Authors API

#### List Authors
```bash
GET http://localhost:8000/api/autor
```

#### Create Author
```bash
POST http://localhost:8000/api/autor
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@ejemplo.com",
  "orcid": "0000-0001-2345-6789",
  "nacionalidad": "Ecuatoriana",
  "telefono": "0999999999",
  "institucion": "ESPE"
}
```

### Books API

#### List Books
```bash
GET http://localhost:8000/api/libros
```

#### Create Book
```bash
POST http://localhost:8000/api/libros
Content-Type: application/json

{
  "titulo": "Libro de Prueba",
  "anioPublicacion": 2024,
  "editorial": "Editorial X",
  "isbn": "1234567890",
  "resumen": "Resumen del libro",
  "idioma": "Español",
  "genero": "Ciencia",
  "numeroPaginas": "200",
  "edicion": "Primera",
  "autor": { "id": 1 }
}
```

### Articles API

#### Create Article
```bash
POST http://localhost:8000/api/articulos
Content-Type: application/json

{
  "titulo": "Artículo de Prueba",
  "anioPublicacion": 2024,
  "editorial": "Editorial Y",
  "isbn": "9876543210",
  "resumen": "Resumen del artículo",
  "idioma": "Español",
  "doi": "10.1234/abcd.2024.01",
  "revista": "Revista Científica",
  "volumen": "10",
  "numero": "2",
  "paginas": "100-110",
  "mesPublicacion": "Junio",
  "tipoArticulo": "Investigación",
  "autor": { "id": 1 }
}
```

### Notifications API

```bash
GET http://localhost:8000/api/v1/notificacion
```

## 🧪 Testing

### Load Testing with Locust

Locust is included for performance and load testing.

#### Install Locust

```bash
pip install locust
```

#### Run Load Tests

```bash
cd locust
locust -f test.py --host=http://localhost:8000
```

Then open http://localhost:8089 in your browser to configure and start the test.

#### Test Configuration

The `test.py` file includes tests for:
- Creating books
- Creating articles
- Creating authors (commented out by default)

**Sample Test Results:**
- Concurrent users: Configurable
- Wait time: 0.5-1.5 seconds between requests
- Target: Books and Articles creation endpoints

### Manual Testing

Use the provided Postman routes in `mis-publicaciones/postman-routes.md` for manual API testing.

## ☁️ AWS Deployment

This application has been tested and deployed on AWS using the following services:

### AWS Services Used

1. **Amazon EKS (Elastic Kubernetes Service)**
   - Managed Kubernetes cluster
   - Auto-scaling groups for nodes

2. **Amazon EC2**
   - Worker nodes for Kubernetes
   - Instance types: t3.medium or larger recommended

3. **Amazon RDS / CockroachDB Cloud**
   - Managed database option
   - Alternative: Self-managed CockroachDB on EC2

4. **Application Load Balancer**
   - Ingress controller integration
   - SSL/TLS termination

5. **Amazon ECR (Elastic Container Registry)**
   - Store Docker images
   - Integration with EKS

### Deployment Steps (AWS)

#### 1. Create EKS Cluster

```bash
eksctl create cluster \
  --name boleteria-microservices \
  --region us-east-1 \
  --nodegroup-name standard-workers \
  --node-type t3.medium \
  --nodes 3 \
  --nodes-min 1 \
  --nodes-max 4 \
  --managed
```

#### 2. Configure kubectl

```bash
aws eks update-kubeconfig --name boleteria-microservices --region us-east-1
```

#### 3. Create ECR Repositories

```bash
# Create repositories for each service
for service in eureka-server api-gateway auth publicaciones catalogo notificaciones sincronizacion; do
  aws ecr create-repository --repository-name ms-$service --region us-east-1
done
```

#### 4. Build and Push Images

```bash
# Login to ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <aws-account-id>.dkr.ecr.us-east-1.amazonaws.com

# Build and push
docker tag ms-eureka-server:latest <aws-account-id>.dkr.ecr.us-east-1.amazonaws.com/ms-eureka-server:latest
docker push <aws-account-id>.dkr.ecr.us-east-1.amazonaws.com/ms-eureka-server:latest

# Repeat for all services...
```

#### 5. Update Kubernetes Manifests

Update image references in `kubernetes/*.yml` files to point to your ECR repositories.

#### 6. Deploy to EKS

```bash
# Apply all Kubernetes configurations
kubectl apply -f kubernetes/
```

#### 7. Configure Load Balancer

```bash
# Install AWS Load Balancer Controller
kubectl apply -k "github.com/aws/eks-charts/stable/aws-load-balancer-controller//crds?ref=master"

# Update ingress.yml with proper annotations for ALB
```

### Cost Optimization Tips

- Use Spot Instances for non-critical services
- Enable cluster autoscaler
- Use Fargate for appropriate workloads
- Set up CloudWatch logs retention policies
- Use NAT Gateway efficiently

## 📁 Project Structure

```
boleteria-springboot-microservicios/
├── bdd/                          # Database configurations
│   └── docker-compose.yaml       # CockroachDB cluster setup
├── kubernetes/                   # K8s deployment files
│   ├── namespace.yml            # Namespace definition
│   ├── configmap.yml            # Environment variables
│   ├── eureka.yml               # Eureka server deployment
│   ├── gateway.yml              # API Gateway deployment
│   ├── auth.yml                 # Auth service deployment
│   ├── publicaciones.yml        # Publications service
│   ├── catalogo.yml             # Catalog service
│   ├── notificaciones.yml       # Notifications service
│   ├── sincronizacion.yml       # Sync service
│   ├── rabbitmq.yml             # RabbitMQ deployment
│   ├── cockroach-service.yml    # CockroachDB StatefulSets
│   ├── cockroach-job.yml        # DB initialization job
│   └── ingress.yml              # Ingress configuration
├── locust/                      # Load testing
│   └── test.py                  # Locust test scenarios
├── ms-eureka-server/            # Service discovery
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── ms-api-gateway/              # API Gateway
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── ms-auth/                     # Authentication service
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── mis-publicaciones/           # Publications service
│   ├── src/
│   ├── Dockerfile
│   ├── postman-routes.md       # API documentation
│   └── pom.xml
├── ms-catalogo/                 # Catalog service
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── ms-notificaciones/           # Notifications service
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── sincronizacion/              # Synchronization service
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── microservicio-seguridad.md   # Security documentation
├── .gitignore
└── README.md
```

## 🔐 Security Considerations

1. **JWT Tokens**: All protected endpoints require valid JWT tokens
2. **Role-Based Access Control**: Admin and User roles with different permissions
3. **Database Security**: CockroachDB runs in insecure mode for development (enable secure mode for production)
4. **Environment Variables**: Sensitive data should be stored in Kubernetes secrets
5. **Network Policies**: Implement K8s network policies for production

### Production Security Checklist

- [ ] Enable secure mode for CockroachDB
- [ ] Use Kubernetes Secrets for sensitive data
- [ ] Implement HTTPS/TLS for all services
- [ ] Set up proper firewall rules
- [ ] Enable audit logging
- [ ] Implement rate limiting
- [ ] Use secret rotation
- [ ] Enable Pod Security Policies

## 🔧 Configuration

### Environment Variables

Key environment variables (configured in `kubernetes/configmap.yml`):

```yaml
EUREKA_SERVER_URL: http://eureka-service:8761/eureka/
RABBITMQ_HOST: rabbitmq-service
RABBITMQ_PORT: 5672
RABBITMQ_USERNAME: admin
RABBITMQ_PASSWORD: admin
DB_USERNAME: root
DB_PASSWORD: ""
JWT_SECRET: <base64-encoded-secret>
JWT_EXPIRATION_MS: 3600000
```

### Application Ports

- Eureka Server: 8761
- API Gateway: 8000
- CockroachDB Node 1: 26257
- CockroachDB Node 2: 26258
- CockroachDB Node 3: 26259
- RabbitMQ AMQP: 5672
- RabbitMQ Management: 15672

## 🐛 Troubleshooting

### Common Issues

#### Services not registering with Eureka

```bash
# Check Eureka server logs
kubectl logs -n microservicios deployment/eureka-server

# Verify EUREKA_SERVER_URL in configmap
kubectl get configmap app-config -n microservicios -o yaml
```

#### Database connection issues

```bash
# Check CockroachDB status
kubectl exec -it crdb-node1-0 -n microservicios -- ./cockroach node status --insecure

# Test database connectivity
kubectl exec -it crdb-node1-0 -n microservicios -- ./cockroach sql --insecure -e "SHOW DATABASES;"
```

#### RabbitMQ connection issues

```bash
# Check RabbitMQ logs
kubectl logs -n microservicios deployment/rabbitmq

# Access RabbitMQ management
kubectl port-forward -n microservicios svc/rabbitmq-service 15672:15672
```

## 📊 Monitoring

### Health Checks

All services expose Spring Boot Actuator endpoints:

```bash
# Check service health
curl http://localhost:8000/actuator/health

# View all actuator endpoints
curl http://localhost:8000/actuator
```

### Kubernetes Monitoring

```bash
# View pod status
kubectl get pods -n microservicios -w

# View logs
kubectl logs -f -n microservicios deployment/api-gateway

# View events
kubectl get events -n microservicios --sort-by='.lastTimestamp'
```

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Coding Standards

- Follow Java code conventions
- Use meaningful variable and method names
- Write unit tests for new features
- Update documentation as needed
- Use Lombok annotations to reduce boilerplate

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Erick Lasluisa** - [ericklasluisa](https://github.com/ericklasluisa)

## 🙏 Acknowledgments

- Spring Boot and Spring Cloud teams
- Netflix OSS team for Eureka
- CockroachDB team
- RabbitMQ team
- Kubernetes community

## 📞 Support

For support, please open an issue in the GitHub repository or contact the maintainers.

---

**Happy Coding! 🚀**
