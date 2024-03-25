# Software Engineering 1 Assignment (Database)

## Prerequisites

-  Docker Desktop: https://www.docker.com/products/docker-desktop/
-  PostgreSQL Command Line Tools (Available in the PostgreSQL installer): https://www.postgresql.org/download/

When running the PostgreSQL installer, ensure that you untick 'PostgreSQL Server' to prevent clashes with docker later on. Furthermore, ensure the 'bin' folder in the newly installed PostgreSQL directory is added to your PATH environment variable.

## Database Setup for Local Development

Ensure the prerequisites have been installed on your machine before proceeding.

To set up the database, open a terminal in this directory and run the following commands:

```bash
docker-compose up -d
pg_restore -d exeterairways -U exeter -h localhost exeterairwaysdb.bak # Password: exeter
```

This will create a PostgreSQL database instance and restore it from the backup file provided. Upon connecting, you should find all of the database tables with some prepopulated data.