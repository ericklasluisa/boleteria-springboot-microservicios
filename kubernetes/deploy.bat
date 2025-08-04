@echo off
echo ========================================
echo   CONFIGURACION COMPLETA DESDE CERO
echo ========================================

echo [1] Verificando Minikube...
minikube status
if %errorlevel% neq 0 (
    echo ERROR: Minikube no está corriendo
    echo Ejecuta: minikube start --driver=docker
    pause
    exit /b 1
)

echo [2] Creando namespace...
kubectl apply -f namespace.yml

echo [3] Aplicando ConfigMap...
kubectl apply -f configmap.yml

echo [4] Desplegando RabbitMQ...
kubectl apply -f rabbitmq.yml
echo Esperando RabbitMQ...
kubectl wait --for=condition=available deployment/rabbitmq -n microservicios --timeout=120s

echo [5] Desplegando Eureka Server...
kubectl apply -f eureka.yml
echo Esperando Eureka...
kubectl wait --for=condition=available deployment/eureka-server -n microservicios --timeout=120s

echo [6] Desplegando CockroachDB...
kubectl apply -f cockroach-service.yml
echo Esperando que los pods de CockroachDB estén listos (puede tomar 2-3 minutos)...
kubectl wait --for=condition=ready pod -l app=cockroachdb -n microservicios --timeout=300s

echo [7] Inicializando cluster CockroachDB...
timeout /t 30
kubectl exec -it crdb-node1-0 -n microservicios -- /cockroach/cockroach init --insecure --host=crdb-node1:26257

echo [8] Creando bases de datos...
kubectl exec -it crdb-node1-0 -n microservicios -- /cockroach/cockroach sql --insecure --host=crdb-node1:26257 --execute="CREATE DATABASE IF NOT EXISTS auth_db; CREATE DATABASE IF NOT EXISTS publicaciones_db; CREATE DATABASE IF NOT EXISTS catalogo_db; CREATE DATABASE IF NOT EXISTS notificaciones_db; SHOW DATABASES;"

echo [9] Desplegando microservicios...
kubectl apply -f auth.yml
kubectl apply -f publicaciones.yml
kubectl apply -f catalogo.yml
kubectl apply -f notificaciones.yml

echo [10] Desplegando servicios adicionales...
kubectl apply -f sincronizacion.yml

echo [11] Desplegando API Gateway...
kubectl apply -f gateway.yml

echo [12] Aplicando Ingress (opcional)...
kubectl apply -f ingress.yml

echo [13] Esperando que todos estén listos...
kubectl wait --for=condition=available deployment --all -n microservicios --timeout=300s

echo [14] Verificando estado final...
kubectl get pods -n microservicios
kubectl get services -n microservicios

echo ========================================
echo   CONFIGURACION COMPLETADA
echo ========================================
echo.
echo Servicios disponibles:
echo - Eureka: kubectl port-forward service/eureka-service 8761:8761 -n microservicios
echo - API Gateway: kubectl port-forward service/api-gateway-service 8000:8000 -n microservicios
echo - RabbitMQ: kubectl port-forward service/rabbitmq-service 15672:15672 -n microservicios
echo - CockroachDB Node 1: kubectl port-forward service/crdb-node1 8081:8080 -n microservicios
echo - Sincronizacion: kubectl port-forward service/sincronizacion-service 8080:8080 -n microservicios
echo.
echo Acceso con NodePort:
echo - API Gateway: minikube service api-gateway-service -n microservicios --url
echo.
echo Usuario RabbitMQ: admin / admin
echo.
echo Para Ingress (si tienes nginx habilitado):
echo - App: http://app-publicaciones.local