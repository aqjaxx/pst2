
# API Rest para rotina de pagamentos e transações

Esta é uma solução para o teste – desafio da sele Pismo, referente a processamento de pagamentos.
A aplicação disponibiliza endpoints para que acessados contabilizam transações financeiras no banco de dados


### Build With

* [Apache Groovy] (http://groovy-lang.org/) - Linguagem de programação tipada que possui fácil integração com o Java e roda na JVM.
* [Eclipse Vert.x] (http://vertx.io/) - Framework/toolkit para desenvolvimento de aplicações reativas, rodando na JVM.
* [Oracle MySQL] (https://www.mysql.com/) - Banco de dados relacional de alto desempenho e confiável.


### O código

Dividido em duas camadas, uma interface para o acesso da API (package teste2.pismo.App)  e outra parte com persistência no banco (package teste2.pismo.pisdb).
A disponibilização do acesso à internet é gerenciado pelo Vert.x, podendo ser escalado utilizando os próprios recursos disponibilizados pelo framework.

No pacote constam os scripts SQL, para gerar as tabelas do banco de dados e valores iniciais, além de scripts de manutenção, biblioteca JDBC para MySQL e scripts para gerar contêineres Docker (um para o banco de dados e outro para a API propriamente dita)


### Para testar/Gerar contêiner 

Disponibilizado a possibilidade de gerar dois contêineres, um banco de dados e outro a API. Denota-se que o Banco de Dados é dispinibilizado somente para testes, visto que num ambiente de produção já deve haver database centralizado. O código pode ser adaptado facilmente para acessar qualquer outro database.

* Gerando o contêiner do MySQL -
	O script Dockerfile na pasta ‘sql’ gerará um contêiner com o banco de dados MySQL e inserirá os scripts sqls para gerar as tabelas e também inserir os valores iniciais para teste (os mesmos do documento desafio).

	Para rodar basta executar os comandos a partir da pasta sql:
```
sudo docker build -t mydb .
sudo docker run -d -p 3306:3306 -i -t --name=pismo2db mydb
```

Depois de gerar e executar o contêiner é necessário executar o script de dentro do contêiner:

```
sudo docker exec pismo2db /usr/sql/inicializa.sh
```

* Gerando o contêiner com o Vert.x e API
	Na pasta principal contém o Dockerfile para gerar o conteúdo principal do projeto. Gerará um contêiner com o Vertx,  inserirá a biblioteca para acessar a o JDBC/MySQL e copiará os arquivos (os fontes mesmo, compilados em tempo de execução pelo Groovy).

	Segue os comandos, a serem executados a partir da pasta do projet:o:
```
sudo docker build -t myapi .
sudo docker run -d -p 8080:8080 -i -t --name=pismo2api myapi
```

Caso a API tenha conflito para acessar o banco de dados (quando, rodando os dois contêineres ou quando o servidor MySQL está em outro endereço IP) pode-se executar a ferramenta para atualizar o endereço IP do banco:

```
sudo docker exec -t -i /usr/verticles/endereca_mysql <endereco_ip>
```
A partir daqui temos a API “escutando” os comandos que podem ser acessados a partir da porta 8080 


### As API disponibilizadas -

*Accounts
```
PATCH /v1/accounts/<id>
{
        "available_credit_limit": {
                "amount": 123.45 // enviar valor negativo para subtrair
        },

        "available_withdrawal_limit": {
                "amount": 123.45 // enviar valor negativo para subtrair
        }
}

GET /v1/accounts/limits
```
*Transactions
```
POST /v1/transactions
{
        "account_id": 1,
        "operation_type_id": 1,
        "amount": 123.45
}

POST /v1/payments (pode enviar multiplos pagamentos)
[
        {
                "account_id": 1,
                "amount": 123.45
        },

        {
                "account_id": 1,
                "amount": 456.78
        }
]
```



