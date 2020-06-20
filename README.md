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

## Demonstrações - Walkthrough

0. [Criação de um Projeto Quarkus](#execute-step-0)
1. [Execução do Projeto](#execute-step-1)
2. [Geração do Pacote](#execute-step-2)
3. [Compilações Quarkus](#execute-step-3)
4. [Restfull WebServices](#execute-step-4)
5. [Adicionar suporte a JSON-B](#execute-step-5)
6. [Criação da Camada de Service - Dependency Injection](#execute-step-6)
7. [Adicionar suporte a OpenAPI/Swagger](#execute-step-7)
8. [Inclusão Persistência - Hibernate Panache](#execute-step-8)
9. [Inclusão BuscaCEP - MicroProfile Rest Client](#execute-step-9)
10. [Inclusão Monitoramento - MicroProfile Metrics](#execute-step-10)
11. [Implementar Tolerância Falha - MicroProfile Fault Tolerance](#execute-step-11)
12. [Segurança - Keycloak/OAuth/OIDC/JWT](#execute-step-12)
13. [Implementar APM - OpenTracing](#execute-step-13)

### 0 - Criação de um Projeto Quarkus <a name="execute-step-0">

* Criação de um projeto básico com o *VSCode* e seu respectivo plugin para *Quarkus*

  ```
  Cmd/Ctrl + Shift + P
  Generate a Quarkus Project
  Selecione a ferramenta de build de sua escolha: Maven ou Gradle
  Informe o GAV (Group, Artifact, Version)
  Escolha o nome do recurso (ex. GreetingResource)
  Não adicione nenhuma extensão adicional, portanto, aperte <ENTER>
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

* Verifique o tamanho do arquivo *jar* gerado,  quantidade de memória utilizada e tempo de *bootstrap*:

  ```
  du -sh ./target/*runner.jar  ./target/lib
  ps -o pid,rss,command -p <pid>
  ```
  * arquivo *"-runner.jar"* consiste no *jar* executável. Necessária a presença do diretório */lib*. Maiores detalhes nesse [LINK](https://quarkus.io/guides/getting-started#packaging-and-run-the-application)
  * caso necessite criar um *uber-jar*, basta adicionar as seguintes properties no arquivo *application.properties*. Maiores detalhes nesses links: [MAVEN](https://quarkus.io/guides/maven-tooling#uber-jar-maven) [GRADLE](https://quarkus.io/guides/gradle-tooling#building-uber-jars)

### 3 - Compilações Quarkus <a name="execute-step-3">

* Maiores detalhes sobre esses processos estão disponíveis nesses links: [Native Image](https://quarkus.io/guides/building-native-image) [Container Image](https://quarkus.io/guides/container-image)

* Geração de uma imagem Docker com binário tradicional:

  ```
  docker build -f src/main/docker/Dockerfile.jvm -t getting-started-jvm .
  docker images | grep 'quarkus-quickstart'
  ```

* Geração de uma imagem Docker com binário compilado nativamente (macOS):

  ```
  ./mvnw package -Pnative -DskipTests
  file <file>
  ./target/<file>
  docker build -f src/main/docker/Dockerfile.native -t getting-started-native .
  docker images | grep 'getting-started-native'
  ```

* Geração de uma imagem Docker com binário compilado nativamente (Linux):

  ```
  ./mvnw package -Pnative -Dquarkus.native.container-build=true -DskipTests
  docker build -f src/main/docker/Dockerfile.native -t getting-started-native-linux .
  docker images | grep 'getting-started-native-linux'
  ```

* Execução das imagens:

  ```
  docker run -it -p 8080:8080 getting-started-jvm
  docker run -it -p 8180:8080 getting-started-native
  docker run -it -p 8280:8080 getting-started-native-linux
  ```

* Monitoramento dos Containers:

  ```
  docker stats
  ```

* Execução Benchmarks de Performance:

  ```
  ab -n 50000 -c 10 http://localhost:8080/hello
  ab -n 50000 -c 10 http://localhost:8280/hello
  ```

### 4 - Restfull WebServices <a name="execute-step-4">

* Criação de um projeto básico com o *VSCode* e seu respectivo plugin para *Quarkus*:

  ```
  Cmd/Ctrl + Shift + P
  Generate a Quarkus Project
  Selecione a ferramenta de build de sua escolha: Maven ou Gradle
  Informe o GAV (Group, Artifact, Version)
  Escolha o nome do recurso (ex. CustomerResource)
  Não adicione nenhuma extensão adicional, portanto, aperte <ENTER>
  Selecione diretório destino do projeto gerado
  ```

* Criação possível através do [Quarkus Initializer](https://code.quarkus.io/)

* Criação possível através do *Maven* de maneira tradicional:

  ```
  mvn io.quarkus:quarkus-maven-plugin:1.5.2.Final:create \
    -DprojectGroupId=br.com.redhat.quarkus \
    -DprojectArtifactId=customer \
    -DprojectVersion=1.0.0-SNAPSHOT \
    -DclassName="br.com.redhat.quarkus.CustomerResource"
  ```

  * por padrão o projeto é criado utilizando JDK 11, portanto, caso sua JVM seja de uma versão inferior, por exemplo 1.8, é necessário alteração arquivo *pom.xml*:

    ```
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    ```

* Inicialização da aplicação em *dev mode*:

  ```
  ./mvnw compile quarkus:dev
  ```
  * maiores detalhem em [Development Mode](https://quarkus.io/guides/getting-started#development-mode)

* Mudança do *Produces* para *MediaType.APPLICATION_JSON*:

  ```
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String hello() {
      return "hello";
  }
  ```

* Criação do Domínio Customer:

  ```
  package br.com.redhat.quarkus;

  public class Customer {

    public String primeiroNome;
    public Long rg;
    public String sobreNome;


	public String getPrimeiroNome() {
		return primeiroNome;
	}
	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}
	public Long getRg() {
		return rg;
	}
	public void setRg(Long rg) {
		this.rg = rg;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		return true;
	}

  }
  ```

### 5 - Adicionar suporte a JSON-B <a name="execute-step-5">

* Maiores detalhes em na documentação [JSON-B](https://quarkus.io/guides/rest-json#configuring-json-support)

* Modificar o *Restfull WebService* para retornar a entidade *Customer*:

  ```
  public Customer hello() {
      Customer customer = new Customer();
      customer.setPrimeiroNome("nome1");
      customer.setSobreNome("sobreNome1");
      customer.setRg(3843748L);
      return customer;
  }
  ```

* Ao tentar invocar o *WebService* o seguinte erro é esperado:

  ```
  http :8080/hello    
  HTTP/1.1 500 Internal Server Error
  Content-Length: 124
  Content-Type: text/html;charset=UTF-8

  Could not find MessageBodyWriter for response object of type: br.com.redhat.quarkus.Customer of media type: application/json
  ```

* Incluir *extension* **RestEasy JSON-B**:

  ```
  Quarkus: Add extensions to current project
  RESTEasy JSON-B
  ```

* Modificar *path* do *WebService* para **Customers**:

  ```
  @Path("/customers")
  public class CustomerResource {
  ```

* Ao tentar invocar o *WebService* o seguinte retorno é esperado:

  ```
  HTTP/1.1 200 OK
  Content-Length: 62
  Content-Type: application/json

  {
    "primeiroNome": "nome1",
    "rg": 3843748,
    "sobreNome": "sobreNome1"
  }
  ```

### 6 - Criação da Camada de Service - Dependency Injection <a name="execute-step-6">

* Referência do suporte a [CDI](https://quarkus.io/guides/cdi-reference) pelo **Quarkus**

* Criar a classe **CustomerService**:

  ```
  @ApplicationScoped
  public class CustomerService {

      private Set<Customer> customerSet = new HashSet<Customer>(0);

      public Customer addCustomer(Customer customer){
          if (customerSet.contains(customer)){
              for (Customer customerEntity : customerSet) {
                  if (customerEntity.equals(customer)){
                      return customerEntity;
                  }
              }
          }
          customerSet.add(customer);
          return customer;
      }

      public Customer getCustomer(Customer customer){
          if (customerSet.contains(customer)){
              for (Customer customerEntity : customerSet) {
                  if (customerEntity.equals(customer)){
                      return customerEntity;
                  }
              }
          }
  		return null;
      }

      public Set<Customer> listCustomer(){
          return customerSet;
      }

      public Customer deleteCustomer(Customer customer){
          if (customerSet.contains(customer)){
              customerSet.remove(customer);
              return customer;
          }
          return null;
      }

      public Customer updateCustomer(Customer customer){
          if (customerSet.contains(customer)){
              for (Customer customerEntity : customerSet) {
                  if (customerEntity.equals(customer)){
                      customerEntity.setRg(customerEntity.getRg());
                      customerEntity.setPrimeiroNome(customer.getPrimeiroNome());
                      customerEntity.setSobreNome(customer.getSobreNome());
                      return customerEntity;
                  }
              }
          }
          return null;
      }

  }
  ```

* Customizar a classe **CustomerResource**:

  ```
  @Path("/customers")
  public class CustomerResource {

      @Inject
      CustomerService customerService;

      @GET
      @Produces(MediaType.APPLICATION_JSON)
      public Set<Customer> listCustomer(){
          return customerService.listCustomer();
      }

      @GET
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
      @Path("/rg/{rg}")
      public Customer getCustomer(@PathParam("rg") Long rg){
          Customer customerEntity = new Customer();
          customerEntity.setRg(rg);
          customerEntity = customerService.getCustomer(customerEntity);
          return customerEntity;
      }

      @POST
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
      public Customer addCustomer(Customer customer){
          Customer customerEntity = customerService.addCustomer(customer);
          return customerEntity;
      }

      @PUT
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
      public Customer updatCustomer(Customer customer){
          Customer customerEntity = customerService.updateCustomer(customer);
          return customerEntity;
      }

      @DELETE
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
      @Path("/rg/{rg}")
      public Customer deleteCustomer(@PathParam("rg") Long rg){
          Customer customerEntity = new Customer();
          customerEntity.setRg(rg);
          customerEntity = customerService.deleteCustomer(customerEntity);
          return customerEntity;
      }

  }
  ```

* Execução de Testes da *RestFull API* **CustomerResource**:

  ```
  http :8080/customers
  http POST :8080/customers rg=11111 primeiroNome=nome1 sobreNome=sobrenome1
  http POST :8080/customers rg=22222 primeiroNome=nome2 sobreNome=sobrenome2
  http :8080/customers/rg/11111
  http :8080/customers/rg/22222
  http PUT :8080/customers rg=11111 primeiroNome=nome1Editado sobreNome=sobrenome1Editado
  http :8080/customers/rg/11111
  http :8080/customers
  http DELETE :8080/customers/rg/11111
  http :8080/customers
  ```

### 7 - Adicionar suporte a OpenAPI/Swagger <a name="execute-step-7">

* Maiores detalhes em na documentação [OpenAPI](https://quarkus.io/guides/openapi-swaggerui)

* Incluir *extension* **Quarkus SmallRye OpenApi**:

  ```
  Quarkus: Add extensions to current project
  SmallRye OpenAPI
  ```

* Habilitar *Swagger UI* em ambiente *produtivo*:

  ```
  application.properties
  quarkus.swagger-ui.always-include = true
  ```

* Acessar *Swagger UI* ou *OpenAPI Endpoint*:

  ```
  http://localhost:8080/swagger-ui/
  http :8080/openapi
  ```

* Criação da classe **CustomerAPIApplication**

  ```
  import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
  import org.eclipse.microprofile.openapi.annotations.info.Contact;
  import org.eclipse.microprofile.openapi.annotations.info.Info;
  import org.eclipse.microprofile.openapi.annotations.info.License;
  import org.eclipse.microprofile.openapi.annotations.tags.Tag;

  @OpenAPIDefinition(
      tags = {
          @Tag(name="customers", description="API de Gerenciamento de Customers"),
      },
      info = @Info(
          title="Customers API",
          version = "1.0.0",
          contact = @Contact(
              name = "Customers API Support",
              url = "http://customersapi.com/contact",
              email = "customersapi@customersapi.com"),
          license = @License(
              name = "Apache 2.0",
              url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
  )
  public class CustomerAPIApplication extends Application{

  }
  ```

* Acessar *Swagger UI* ou *OpenAPI Endpoint*:

  ```
  http://localhost:8080/swagger-ui/
  http :8080/openapi
  ```

### 8 - Inclusão Persistência - Hibernate Panache <a name="execute-step-8">

* Maiores detalhes em na documentação [Hibernate Panache]https://quarkus.io/guides/hibernate-orm-panache)

* Incluir *extensions* **Hibernate ORM with Panache** e  **JDBC Driver PostgreSQL**:

  ```
  Quarkus: Add extensions to current project
  Hibernate ORM with Panache
  JDBC Driver - PostgreSQL
  ```

* Habilitar *Swagger UI* em ambiente *produtivo*:

  ```
  application.properties
  quarkus.swagger-ui.always-include = true
  ```

* Acessar *Swagger UI*:

  ```
  http://localhost:8080/swagger-ui/
  ```

* Maiores detalhes em na documentação [Hibernate Panache](https://quarkus.io/guides/hibernate-orm-panache)

* Incluir *extension* **Hibernate ORM with Panache**:

  ```
  Quarkus: Add extensions to current project
  Hibernate ORM with Panache
  JDBC Driver - PostgreSQL
  ```

* Habilitar *Swagger UI* em ambiente *produtivo*:

  ```
  application.properties
  quarkus.swagger-ui.always-include = true
  ```

* Acessar *Swagger UI*:

  ```
  http://localhost:8080/swagger-ui/
  ```


## Referências Adicionais <a name="additional-references">

- [Tutoriais Hands On - developers.redhat.com](https://developers.redhat.com/courses/quarkus/)
- [Tutoriais Hands On - learn.openshift.com](https://learn.openshift.com/middleware/courses/middleware-quarkus/)
- [Exemplos & Guias](https://quarkus.io/guides/)
- [Quarkus Blog](https://quarkus.io/blog/)
- [Quarkus @ Youtube](https://www.youtube.com/channel/UCaW8QG_QoIk_FnjLgr5eOqg)
- [Red Hat Developers](https://developers.redhat.com/)
- [Red Hat Developers @ Youtube](https://www.youtube.com/channel/UC7noUdfWp-ukXUlAsJnSm-Q)
