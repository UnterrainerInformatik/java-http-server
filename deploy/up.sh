#!/usr/bin/env bash
. ./.gitlab-env

mkdir -p /app/data/http-server/mysql-data

docker-compose pull
docker-compose up -d --force-recreate --remove-orphans &
