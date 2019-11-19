#!/usr/bin/env bash

let doTests=0
let rebuild=0
let deploy=0
while test $# -gt 0; do
           case "$1" in
                -it)
                    shift
                    doTests=1
                    shift
                    ;;
                -b)
                    shift
                    rebuild=1
                    shift
                    ;;
                -d)
                    shift
                    deploy=1
                    shift
                    ;;
                *)
                   echo "$1 is not a recognized flag!"
                   return 1;
                   ;;
          esac
  done

  if test ${doTests} -eq 1 && ${rebuild} -eq 0; then
       echo "Running integration tests..."
       mvn integration-test
  elif test ${rebuild} -eq 1 && ${doTests} -eq 0; then
       echo "Rebuilding package..."
       mvn package
  else
       echo "Running integration tests and rebuilding package..."
       mvn verify
  fi

  if test ${deploy} -eq 1; then
       echo "Deploying on docker..."
       docker-compose up --build -d
  fi
