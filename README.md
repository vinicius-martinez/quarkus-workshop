# Quarkus Workshop

## Descrição

Esse *workshop* tem o objetivo de ilustrar algumas capacidades do [Quarkus](https://quarkus.io/). A *stack* para desenvolvimento Java para aplicações *Cloud Native* e *Serverless*.

## Ambiente

- [JDK/Open JDK 1.8 (no mínimo)](https://openjdk.java.net/install/)
- [Apache Maven 3.6.3](https://maven.apache.org/download.cgi)
- [GraalVM 19.3](https://www.graalvm.org/docs/release-notes/19_3/)
- [Visual Studio Code](https://code.visualstudio.com/)
- [Visual Studio Code Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- [Quarkus Tools for Visual Studio Code](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-quarkus)

## Demonstrações

0. [Introduction](#execute-step-0)
1. [Create an Invoice](#execute-step-1)
2. [Get all Invoices on Red Hat Data Grid](#execute-step-2)
3. [Get an Invoice by Id](#execute-step-3)
4. [Get an Invoice by Id using Hystrix](#execute-step-4)
5. [Get an Invoice by Customer's Name](#execute-step-5)

### Introduction <a name="execute-step-0">

* This demo demonstrates how to integrate [Red Hat Data Grid](https://www.redhat.com/en/technologies/jboss-middleware/data-grid) and [Red Hat Fuse](https://www.redhat.com/en/technologies/jboss-middleware/fuse) through a simple Credit Card Invoice Offload use case. In order to do that, we've created 5 (five) **Red Hat Fuse routes** which leverages this integration. Also there's an *Open API/Swagger* definition available on: **$openshift-modern-invoice-route/fuse/api-doc**.

### Create an Invoice <a name="execute-step-1">

* This is a *REST* endpoint responsible for creating an Invoice. In order to use it, just hit a *HTTP POST* on **$openshift-modern-invoice-route/fuse/invoice** with a content similar as follows:

  ```
  http POST modern-invoice-demo.app.myopenshift.com/fuse/invoice customerName=customer1 dueDate="may/2019" total=5500.45
  ```

### Get all Invoices on Database <a name="execute-step-2">

* This is a *REST* endpoint responsible for fetching all invoices available in our database. In order to use it, just hit a *HTTP GET* on **$openshift-modern-invoice-route/fuse/invoice**:

  ```
  http GET modern-invoice-demo.app.myopenshift.com/fuse/invoice
  ```
  * *TIP :* bare in mind when not using a *MySQL persistent template*, if your *POD* restart for any reason your data will be lost;

### Get an Invoice by Id <a name="execute-step-3">

This is a *REST* endpoint responsible for fetching all invoices available in our system, looking firstly on **Red Hat Data Grid** and if no invoice is found, we're going to hit our database looking for it. In order to use it, just hit a *HTTP GET* on **$openshift-modern-invoice-route/fuse/invoice/${id}**:

  ```
  http GET modern-invoice-demo.app.myopenshift.com/fuse/invoice/1
  ```
  * *TIP 1:* when an invoice is not found on **Red Hat Data Grid** but is available in our *database*, the **Red Hat Fuse Route** will populate our *cache* with this data so the upcoming requests looking for this info, will no longer hit *MySQL*;
  * *TIP 2:* consider reviewing **Red Hat Fuse Route** logs which will display lots of useful information;

### Get an Invoice by Id using Hystrix <a name="execute-step-4">

This is a *REST* endpoint responsible for fetching all invoices available in **Red Hat Data Grid ONLY** and if our cache is not available, our **Fuse Route** will fallback and hit the database to fetch the desired data. In order to use it, just hit a *HTTP GET* on **$openshift-modern-invoice-route/fuse/invoice/hystrix/${id}**:

  ```
  http GET modern-invoice-demo.app.myopenshift.com/fuse/invoice/hystrix/1
  ```
  * *TIP :* this example illustrates how easy you can add resilience on **Red Hat Fuse routes** exploring *Hystrix*

### Get an Invoice by Customer's Name <a name="execute-step-5">

This is a *REST* endpoint responsible for fetching all invoices available in our system, looking firstly on **Red Hat Data Grid** and if no invoice is found, we're going to hit our database looking for it. In order to use it, just hit a *HTTP GET* on **$openshift-modern-invoice-route/fuse/invoice/${customerName}/customer**:

  ```
  http GET modern-invoice-demo.app.myopenshift.com/fuse/invoice/johndoe/customer
  ```
  * *TIP :* unfortunately this **Fuse Route** it not working properly. Please review this [issue](https://github.com/redhat-sa-brazil/modern-invoice-demo/issues/1) for additional details;

## Referências Adicionais <a name="additional-references">

- [Tutoriais Hands On](https://developers.redhat.com/courses/quarkus/)
- [Exemplos & Guias](https://quarkus.io/guides/)
- [Quarkus Blog](https://quarkus.io/blog/)
- [Quarkus @ Youtube](https://www.youtube.com/channel/UCaW8QG_QoIk_FnjLgr5eOqg)
- [Red Hat Developers](https://developers.redhat.com/)
- [Red Hat Developers @ Youtube](https://www.youtube.com/channel/UC7noUdfWp-ukXUlAsJnSm-Q)
