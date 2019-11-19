#!/usr/bin/env bash

let doTests=0
let rebuild=0
let deploy=0
while test $# -gt 0; do
           case "$1" in
                -it|--integration-test)
                    shift
                    doTests=1
                    ;;
                -b|--build)
                    shift
                    rebuild=1
                    ;;
                -d|--deploy)
                    shift
                    deploy=1
                    ;;
                *)
                   echo "$1 is not a recognized flag!"
                   break
                   ;;
          esac
  done

  if test ${doTests} -eq 1 -a ${rebuild} -eq 0; then
       echo "Running integration tests..."
       mvn integration-test
  elif test ${rebuild} -eq 1 -a ${doTests} -eq 0; then
       echo "Rebuilding package..."
       mvn package
  elif test ${rebuild} -eq 1 -a ${doTests} -eq 1; then
       echo "Running integration tests and rebuilding package..."
       mvn verify
  else
       echo "No configuration has been chosen"
  fi

  if test ${deploy} -eq 1; then
       echo "Deploying on docker..."
       docker-compose up --build -d
  fi
