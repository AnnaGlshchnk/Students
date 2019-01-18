#!/bin/bash
docker build -f Dockerfile1 -t sql5 .
docker build -f Dockerfile -t students .
docker-compose up