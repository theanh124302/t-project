# t-project

docker run --name t-postgres -e POSTGRES_DB=t-db -e POSTGRES_USER=t-user -e POSTGRES_PASSWORD=t-password -p 5432:5432 -v D:/Study/t-project/t-postgres-data:/var/lib/postgresql/data -d postgres:15


docker run --name tp-postgres -e POSTGRES_DB=tp-db -e POSTGRES_USER=tp-user -e POSTGRES_PASSWORD=tp-password -p 5433:5432 -v D:/Study/t-project/tp-postgres-data:/var/lib/postgresql/data -d postgres:15

docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.4 start-dev

docker run -d --name redis -p 6379:6379 redis:7


curl push file theo presignedurl : curl -X PUT -H "Content-Type: text/html" --data-binary "@test.html" "https://taprojectbucket.s3.ap-southeast-1.amazonaws.com/application/octet-stream/821e97c8-cd5c-4a93-a798-52fd59e633ba?Content-Type=application%2Foctet-stream&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250415T165527Z&X-Amz-SignedHeaders=host&X-Amz-Expires=179&X-Amz-Credential=AKIAQWXGYDKEESA326XB%2F20250415%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=0615895749a00bfa80ff48ce1cb1951fbd4f9b2638cecfc2ac916a27c62a31e4"

# t-project
docker run --name t-postgres ^
-e POSTGRES_DB=keycloak ^
-e POSTGRES_USER=keycloak ^
-e POSTGRES_PASSWORD=keycloak ^
-p 5432:5432 ^
-v D:/Study/keycloak-db ^
-d postgres:15

docker run -p 8095:8095 -e KAFKA_CLUSTERS_0_NAME=local -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092 -d provectuslabs/kafka-ui


# cassandra 

docker run --name my-cassandra -d -p 9042:9042 cassandra:latest

CREATE KEYSPACE example_keyspace
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

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


# primary db


docker network create pg-network

mkdir D:\pg-replication\primary-data

docker run --name pg-primary ^
  --network pg-network ^
  -e POSTGRES_DB=mydb ^
  -e POSTGRES_USER=postgres ^
  -e POSTGRES_PASSWORD=postgres ^
  -p 5432:5432 ^
  -v D:/pg-replication/primary-data:/var/lib/postgresql/data ^
  -d postgres:15

docker stop pg-primary

listen_addresses = '*'
wal_level = replica
max_wal_senders = 10
max_replication_slots = 10
hot_standby = on


host    replication     postgres        all                     md5

docker start pg-primary


# standby server

mkdir D:\pg-replication\standby-data

powershell -Command "Remove-Item -Path 'D:\pg-replication\standby-data\*' -Recurse -Force -ErrorAction SilentlyContinue"

docker run --rm -it --network pg-network ^
  -v D:/pg-replication/standby-data:/backup ^
  postgres:15 ^
  pg_basebackup -h pg-primary -p 5432 -U postgres -D /backup -Fp -Xs -P -v


powershell -Command "New-Item -Path 'D:\pg-replication\standby-data\standby.signal' -ItemType File -Force"

powershell -Command "Set-Content -Path 'D:\pg-replication\standby-data\postgresql.auto.conf' -Value \"primary_conninfo = 'host=pg-primary port=5432 user=postgres password=postgres application_name=pg-standby'\""

docker run --name pg-standby ^
  --network pg-network ^
  -e POSTGRES_DB=mydb ^
  -e POSTGRES_USER=postgres ^
  -e POSTGRES_PASSWORD=postgres ^
  -p 5433:5432 ^
  -v D:/pg-replication/standby-data:/var/lib/postgresql/data ^
  -d postgres:15


# check 
docker exec -it pg-primary psql -U postgres -d mydb -c "SELECT pid, client_addr, state, pg_wal_lsn_diff(pg_current_wal_lsn(), replay_lsn) AS lag_bytes FROM pg_stat_replication;"

docker exec -it pg-standby psql -U postgres -d mydb -c "SELECT pg_is_in_recovery();"

docker exec -it pg-primary psql -U postgres -d mydb -c "CREATE TABLE test (id serial primary key, name text);"
docker exec -it pg-primary psql -U postgres -d mydb -c "INSERT INTO test (name) VALUES ('test1'), ('test2');"

docker exec -it pg-standby psql -U postgres -d mydb -c "SELECT * FROM test;"


# logical replication 

primary

#wal_level = logical	

create publication my_publication for table test;


standby

standby giờ đây sẽ không cần replication signal mà sẽ là 1 server riêng, có thể đọc, ghi, hoạt động trên cơ chế pub/sub

docker stop pg-standby

docker rm pg-standby

docker run --name pg-subscriber ^
  --network pg-network ^
  -e POSTGRES_DB=mydb ^
  -e POSTGRES_USER=postgres ^
  -e POSTGRES_PASSWORD=postgres ^
  -p 5433:5432 ^
  -v D:/pg-replication/subscriber-data:/var/lib/postgresql/data ^
  -d postgres:15

CREATE TABLE test (id serial primary key, name text);

CREATE SUBSCRIPTION my_subscription CONNECTION 'host=pg-primary port=5432 dbname=mydb user=postgres password=postgres' PUBLICATION my_publication;