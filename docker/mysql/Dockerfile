FROM mysql:5.5
COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/
COPY 3-TIMEZONE.sql /docker-entrypoint-initdb.d/
