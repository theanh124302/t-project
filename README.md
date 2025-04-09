# t-project
docker run --name t-postgres -e POSTGRES_DB=t-db -e POSTGRES_USER=t-user -e POSTGRES_PASSWORD=t-password -p 5432:5432 -v D:/Study/t-project/t-postgres-data:/var/lib/postgresql/data -d postgres:15


docker run --name tp-postgres -e POSTGRES_DB=tp-db -e POSTGRES_USER=tp-user -e POSTGRES_PASSWORD=tp-password -p 5433:5432 -v D:/Study/t-project/tp-postgres-data:/var/lib/postgresql/data -d postgres:15


docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.4 start-dev

# t-project
docker run --name t-postgres ^
-e POSTGRES_DB=keycloak ^
-e POSTGRES_USER=keycloak ^
-e POSTGRES_PASSWORD=keycloak ^
-p 5432:5432 ^
-v D:/Study/keycloak-db ^
-d postgres:15

docker run -p 8095:8095 -e KAFKA_CLUSTERS_0_NAME=local -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092 -d provectuslabs/kafka-ui

-- Tạo keyspace
CREATE KEYSPACE example_keyspace
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

-- Sử dụng keyspace
USE example_keyspace;

-- Tạo bảng
CREATE TABLE users (
  user_id uuid PRIMARY KEY,
  name text,
  email text
);

-- Chèn dữ liệu
INSERT INTO users (user_id, name, email)
VALUES (uuid(), 'Nguyen Van A', 'nguyenvana@example.com');

-- Truy vấn dữ liệu
SELECT user_id FROM users;
