# Quarkus Workshop

## Descrição

Esse *workshop* tem o objetivo de ilustrar algumas capacidades do [Quarkus](https://quarkus.io/). A *stack* para desenvolvimento Java para aplicações *Cloud Native* e *Serverless*.

Para maiores informações, por favor consulte a seção [Referências Adicionais](#additional-references).

## Ambiente

- [JDK/Open JDK 1.8 (no mínimo)](https://openjdk.java.net/install/)
- [Apache Maven 3.6.3](https://maven.apache.org/download.cgi)
- [GraalVM 19.3](https://www.graalvm.org/docs/release-notes/19_3/)
- [Visual Studio Code](https://code.visualstudio.com/)
- [Visual Studio Code Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- [Quarkus Tools for Visual Studio Code](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-quarkus)
- [HTTPie](https://httpie.org/)

## Demonstrações

0. [Criação de um Projeto Quarkus](#execute-step-0)
1. [Execução do Projeto](#execute-step-1)
2. [Geração do Pacote](#execute-step-2)
3. [Compilações Nativas](#execute-step-3)

### 0 - Criação de um Projeto Quarkus <a name="execute-step-0">

* Criação de um projeto básico com o *VSCode* e seu respectivo plugin para *Quarkus*
  ```
  Cmd/Ctrl + Shift + P
  Generate a Quarkus Project
  Selecione a ferramenta de build de sua escolha: Maven ou Gradle
  Informe o GAV (Group, Artifact, Version)
  Escolha o nome do recurso (ex. GreetingResource)
  Não adicione nenhuma extensão adional, portanto, aperte <ENTER>
  Selecione diretório destino do projeto gerado
  ```

* Criação possível através do [Quarkus Initializer](https://code.quarkus.io/)

* Criação possível através do *Maven* de maneira tradicional:
  ```
  mvn io.quarkus:quarkus-maven-plugin:1.5.2.Final:create \
    -DprojectGroupId=my-groupId \
    -DprojectArtifactId=my-artifactId \
    -DprojectVersion=my-version \
    -DclassName="org.my.group.MyResource"
  ```

### 1 - Inicialização de um Projeto Quarkus <a name="execute-step-1">

* Navegue até o diretório do projeto e execute o comando:
  ```
  /mvnw compile quarkus:dev -DskipTests
  ```
* Outra alternativa seria através do *VSCode*:
  ```
  Cmd/Ctrl + Shift + P
  Quarkus: Debug current Quarkus Project
  ```
* Invoque o Endpoint criado:
  ```
  http :8080/hello
  ```
* O seguinte *output* é esperado:
  ```
  http :8080/hello
  HTTP/1.1 200 OK
  Content-Length: 5
  Content-Type: text/plain;charset=UTF-8

  hello
  ```
  * por padrão o projeto é criado utilizando JDK 11, portanto, caso sua JVM seja de uma versão inferior, por exemplo 1.8, é necessário alteração arquivo *pom.xml*:
    ```
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    ```

### 2 - Geração do Pacote <a name="execute-step-2">

* Navegue até o diretório do projeto e execute o comando:
  ```
  ./mvnw package -DskipTests
  ```
* Certifique-se que a aplicação foi compilada com sucesso, e após esse processo, inicialize a mesma:
  ```
  java -jar target/quarkus-getting-started-1.0.0-SNAPSHOT-runner.jar
  ```
* Verifique o tamanho do arquivo *jar* gerado e quantidade de memória utilizada:
  ```
  du -sh ./target/*runner.jar  ./target/lib
  ps -o pid,rss,command -p <pid>
  ```
  * arquivo *"-runner.jar"* consiste no *jar* executável. Necessária a presença do diretório */lib*. Maiores detalhes nesse [LINK](https://quarkus.io/guides/getting-started#packaging-and-run-the-application)
  * caso necessite criar um *uber-jar*, basta adicionar as seguintes properties no arquivo *application.properties*. Maiores detalhes nesses links: [MAVEN](https://quarkus.io/guides/maven-tooling#uber-jar-maven) [GRADLE](https://quarkus.io/guides/gradle-tooling#building-uber-jars)

### 3 - Compilações Nativas <a name="execute-step-3">


## Referências Adicionais <a name="additional-references">

- [Tutoriais Hands On - developers.redhat.com](https://developers.redhat.com/courses/quarkus/)
- [Tutoriais Hands On - learn.openshift.com](https://learn.openshift.com/middleware/courses/middleware-quarkus/)
- [Exemplos & Guias](https://quarkus.io/guides/)
- [Quarkus Blog](https://quarkus.io/blog/)
- [Quarkus @ Youtube](https://www.youtube.com/channel/UCaW8QG_QoIk_FnjLgr5eOqg)
- [Red Hat Developers](https://developers.redhat.com/)
- [Red Hat Developers @ Youtube](https://www.youtube.com/channel/UC7noUdfWp-ukXUlAsJnSm-Q)
