#Implementing Oracle Integration Cloud Service
This is the code repository for [Implementing Oracle Integration Cloud Service](https://www.packtpub.com/virtualization-and-cloud/implementing-oracle-integration-cloud-service?utm_source=github&utm_medium=repository&utm_campaign=9781786460721), published by [Packt](https://www.packtpub.com). It contains all the supporting project files necessary to work through the book from start to finish.
## About the Book
This book will be a comprehensive, hands-on guide to building successful, high-availability integrations on ICS. This book sets out to demonstrate how ICS can be used to effectively implement integrations that work both in the cloud and on premise. It starts with a fast, practical introduction to what ICS can do for your business and then shows how ICS allows you to develop integrations not only quickly but in a way that means they are maintainable and extensible. Gradually it moves into more advanced integrations, showing how to achieve sophisticated results with ICS and work with external applications. Finally the book shows you how to monitor cloud apps and go beyond ICS to build even more powerful integrated applications.
##Instructions and Navigations
All of the code is organized into folders. Each folder starts with a number followed by the application name. For example, Chapter02.

Chapters without code:
1, 12, 13
chapter 1 contains concepts, chapter 12 is monitoring, and chapter 13 shows more info. Hence no code for them.

The code will look like the following:
```
<html>
  <body>
    LET $book := doc("bookstore.xml")/book
    FOR $ch in $book/chapter
    WHERE $book/chapter/num < 10
    ORDER BY $ch/pagecount DESC
    RETURN <h2>{ $ch/title }</h2>
  </body>
</html>
```

Beyond the use of ICS, we have taken the approach of utilizing additional services and tools that are free wherever possible. We will explain in more detail the different tools and services, but let's start by just introducing what is needed:

An Oracle Cloud account. A trial ICS account will be sufficient for most things (as long as you have tried everything in the book within the trial period obviously).
Free accounts with apiary (https://apiary.io/) and Mockable (https://www.mockable.io/). We will introduce these in more detail shortly.
A free edition of SoapUI (https://www.soapui.org/), as we will be using this to initiate many of our use cases. We also make use of Postman (https://www.getpostman.com/) as an alternate option, which can be retrieved freely.
To follow the book, it will be necessary to look at and make minor changes to the XML Schemas. The changes needed are simple enough that they can be done with a simple text editor, you would rather use your preferred development tool, such as JDeveloper. If you want to use JDeveloper, we would recommend adopting a 12.x version, which can be obtained from http://www.oracle.com/technetwork/developer-tools/jdev/overview/index.html. There is, of course, the middle ground with tools such as Xsemmel (https://xsemmel.codeplex.com/) as an open source option, or the market-leading XMLSpy (http://www.altova.com/).
A copy of the schemas and related resources that go with this book. These can be found via the Packt website or via the author's own site at https://oracle-integration.cloud.
The CURL command-line tool to allow us to make simple calls to the ICS API (https://curl.haxx.se/).
VirtualBox is a freely available desktop style virtualization tool and is used to help quickly create an environment in which we can run the Oracle agent technology (https://www.virtualbox.org/).
Several of the chapters also make use of additional external services to demonstrate some of the features such as file-level integration and social web services. To demonstrate such capabilities, we have created accounts with the following services. To follow these chapters, you may wish to do them. The services are as follows:

Google (https://console.developers.google.com/)
Salesforce (https://developer.salesforce.com/)
TimeDB (https://timezonedb.com)
Twitter (https://dev.twitter.com/overview/api)
Trello (https://trello.com/)
Oracle Messaging Cloud Service (https://cloud.oracle.com/messaging)
This book is intended for web developers with no knowledge of WebStorm yet, but who are experienced in JavaScript, Node.js, HTML, and CSS, and reasonably familiar with frameworks such as AngularJS and Meteor.

###Introduction to apiary

To be able to describe, work with, and simulate (mock) web services that use the REST paradigm (explained in Chapter 1, Introducing the Concepts and Terminology) easily and in a way that reflects the sort of integrations you are likely to build with ICS, we have chosen to use Apiary.io (although there are other services on the Internet that offer similar capabilities). We chose Apiary.io as Oracle are developing a closer relationship with apiary with other products, so it may be possible that Oracle will develop its ecosystem for ICS to further incorporate apiary in the future. Apiary also offers a pricing model that allows you to get started without any additional expenditure.

###Introducing Mockable

Cloud services that offer support for SOAP mocking are limited, in part as REST is overtaking SOAP as a more popular way to provide web services and define them using definition syntaxes such as Blueprint and Swagger. We have adopted Mockable.io for this book – driven by its ease of use and simplicity and again free startup model.

Creating an instance of Integration Cloud Service

When you first start using ICS, you will use Oracle's cloud managed website, which will allow to select the service(s) that you want and configure users. In this book, we have deliberately chosen not to write about this process so that we can concentrate on the application of ICS, which generally will be of greater value to you, we believe. The process of managing users is relatively simple. In addition to this, both Oracle and their partners will surely assist any customers in managing environment setup as it is in their interests to see customers using their services successfully.

##Related Products
* [Getting Started with Oracle Public Cloud](https://www.packtpub.com/virtualization-and-cloud/getting-started-oracle-public-cloud?utm_source=github&utm_medium=repository&utm_campaign=9781782178101)

* [Oracle GoldenGate 12c Implementer's Guide](https://www.packtpub.com/big-data-and-business-intelligence/oracle-goldengate-12c-implementers-guide?utm_source=github&utm_medium=repository&utm_campaign=9781785280474)

* [Managing IaaS and DBaaS Clouds with Oracle Enterprise Manager Cloud Control 12c](https://www.packtpub.com/virtualization-and-cloud/managing-iaas-and-dbaas-clouds-oracle-enterprise-manager-cloud-control-12c?utm_source=github&utm_medium=repository&utm_campaign=9781782177708)
###Suggestions and Feedback
[Click here](https://docs.google.com/forms/d/e/1FAIpQLSe5qwunkGf6PUvzPirPDtuy1Du5Rlzew23UBp2S-P3wB-GcwQ/viewform) if you have any feedback or suggestions.
### Download a free PDF

 <i>If you have already purchased a print or Kindle version of this book, you can get a DRM-free PDF version at no cost.<br>Simply click on the link to claim your free PDF.</i>
<p align="center"> <a href="https://packt.link/free-ebook/9781786460721">https://packt.link/free-ebook/9781786460721 </a> </p>