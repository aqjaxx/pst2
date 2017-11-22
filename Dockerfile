# --- vertx para rodar o projeto
FROM vertx/vertx3

ENV VERTX_HOME /usr/local/vertx
ENV VERTICLE_HOME /usr/verticles

# copia lib para jdbc
COPY lib/mysql-connector-java-5.1.44-bin.jar $VERTX_HOME/lib
# copia os arquivos do projeto
COPY /src/teste2/pismo/App/*.groovy $VERTICLE_HOME/teste2/pismo/App/
COPY /src/teste2/pismo/pisdb/*.groovy $VERTICLE_HOME/teste2/pismo/pisdb/
COPY /endereca_mysql.sh $VERTICLE_HOME/
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run teste2/pismo/App/Listen.groovy -cp $VERTICLE_HOME/*"]

EXPOSE 8080
