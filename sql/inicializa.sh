#!/bin/bash
mysql -u root -p$MYSQL_ROOT_PASSWORD -e "source /usr/sql/createPismoDB1.sql;"
mysql -u root -p$MYSQL_ROOT_PASSWORD -e "source /usr/sql/exemploProva.sql;"
