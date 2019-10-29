# SIG

## Running database

#### Postgres

docker run -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123456789 -e POSTGRES_DB=sig -p 5432:5432 postgres

Once the docker container is up and running, connect to the database with the same configurations set for the container 
and run the scripts (from the scripts folder) to initialize the tables. You can run the scripts by just right clicking
on them and selecting `run {NameOfScript}` and selecting the database where it should go.

## Running server

There are two ways:

######1: Terminal

`sbt run` in the terminal at the project folder

######2: IntellIJ Idea

Edit the configurations to run a play app, apply changes and then just click on the run button...

