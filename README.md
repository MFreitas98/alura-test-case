# README #

##### ALURA API #####

## Instalação ##

OBS 1: Conforme requisito BONUS do test-case que seria a aplicacao funcionar inteiramente
via docker-compose, este requisito foi atendido, portanto, apenas o Docker e Docker Compose
bastariam para utilizar a API pois subiria via container, o passo a passo estará abaixo.

OBS 2: Na raiz do projeto existe um arquivo texto com todos os CURL para todos os endpoints da API
e breve explicacoes sobre cada um deles.

Para utilizacao via IDE , será necessário:

* Java
    * Recomendo usar o [SDK Man](https://sdkman.io/install) e instalar a versão
      OpenJDK Temurin 21.
* Docker & Docker Compose
    * Recomendo usar o
      [Docker Desktop](https://www.docker.com/products/docker-desktop/),
      pois ele já traz o docker compose junto.
* Maven
    * Recomendo a instalação do Maven usando o [SDK Man](https://sdkman.io/install)
    * para facilitar a gestão de várias versões. Você pode instalar a versão 3.8.7.
* IDE
    * Recomendo usar a última versão
      [IntelliJ IDEA community](https://www.jetbrains.com/idea/download).
    * Aproveite para adicionar alguns plugins necessários que facilitam o
      desenvolvimento, para isso vá ao menu **Preferences | Plugins**.
        * **Lombok** para diminuir a quantidade de código.
        * **Rainbow Brackets** para facilitar a visualização do código em cores
          diferentes nas chaves, colchetes e parênteses. *Opcional*
        * **JPA Buddy** para manipular recursos relacionados a JPA. *Opcional*
        * **Docker** para gerenciar o docker pelo IDE. *Opcional*
* Database
    * Recomendo usar [DBeaver Community](https://dbeaver.io/download/), para
      acessar o banco de dados MySQL da aplicacão.


## Execução ##

* Na raiz do projeto, execute o seguinte comando para iniciar o ambiente Docker:

    * docker-compose up (caso queria ver os logs)
      OU
    * docker-compose up -d

    * Primeiramente, ele irá compilar a aplicação e, para compilar, irá baixar as imagens do JDK e do Maven.
    * Após isso, como a aplicação utiliza o MySQL como banco de dados, antes de subir a aplicação,
      as imagens do MySQL e do Adminer(para gerenciamento da base) serao baixadas.
    * Por fim, a aplicação estará disponível em: :

  *** http://localhost:8080/api/swagger-ui/index.html#/ ***

* A aplicao possui Spring-Security conforme requisito. O endpoit
  para criar um Usuário na API é aberto, e para conseguir acessar os endpoints
  sugiro criar um usuario ADMIN, segue curl que podera ser utilizado
  até mesmo no terminal:

* curl -X 'POST' \
  'http://localhost:8080/api/users/createUser' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Alura Admin",
  "email": "alura@gmail.com",
  "role": "ADMIN",
  "userName": "aluraadmin",
  "password": "12345"
  }'
*