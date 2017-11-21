# --- database mySql
FROM mysql 
# seta root_password
ENV MYSQL_ROOT_PASSWORD pass123

# copia arquivo para gerar as tabelas no database
RUN	mkdir /usr/sql
RUN	chmod 644 /usr/sql
ADD ["createPismoDB1.sql", "/usr/sql/createPismoDB1.sql"]
ADD ["exemploProva.sql", "/usr/sql/exemploProva.sql"]

EXPOSE 3306
