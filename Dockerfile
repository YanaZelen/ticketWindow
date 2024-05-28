FROM postgres:9.5.23

ADD docker-entrypoint-initdb.d/init.sql /docker-entrypoint-initdb.d/
