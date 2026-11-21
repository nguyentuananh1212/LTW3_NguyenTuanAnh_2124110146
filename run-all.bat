@echo off
title Khoi dong ca hai Microservices
echo ===================================================
echo   KHOI DONG PRODUCT SERVICE & ORDER SERVICE
echo ===================================================
echo Dang khoi dong Product Service tai cong 8081...
start "Product Service (Port 8081)" cmd /k "title Product Service :8081 && set _JAVA_OPTIONS=-Xms64m -Xmx256m -XX:+UseSerialGC && cd /d %~dp0product-service && mvn spring-boot:run"

echo Dang doi 5 giay de Product Service san sang...
timeout /t 5 /nobreak >nul

echo Dang khoi dong Order Service tai cong 8082...
start "Order Service (Port 8082)" cmd /k "title Order Service :8082 && set _JAVA_OPTIONS=-Xms64m -Xmx256m -XX:+UseSerialGC && cd /d %~dp0order-service && mvn spring-boot:run"

echo.
echo ===================================================
echo Ca 2 Services dang duoc chay trong cac cua so rieng!
echo - Product Service: http://localhost:8081/api/products
echo - Order Service:   http://localhost:8082/api/orders
echo - Frontend:        %~dp0frontend\index.html
echo ===================================================
pause
