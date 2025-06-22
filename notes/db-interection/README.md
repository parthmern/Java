# DB interection

* java related  techs depends on <mark style="background-color:blue;">**JDBC ( java database connectivity)**</mark>&#x20;
  * helps in directly DB interetion
  * we can use JDBC to execute in db
  * we write queries in string and pass those function exposed by JDBC
  * ex. [https://www.geeksforgeeks.org/java/establishing-jdbc-connection-in-java/](https://www.geeksforgeeks.org/java/establishing-jdbc-connection-in-java/)
  * this is old skool concept
* next, <mark style="background-color:yellow;">**JPA ( java persistant api**</mark>**)** is specification for accessing, persisting and managing data between java object and relational tables
  * JPA | jdbc&#x20;
  * jpa is kind of abstraction and it made on top of JDBC
  * is set of interfaces and annotations&#x20;
  * use these to connect with DB
  * that's where ORMs ( object relational mapper) comes into pic
    * ex. hibernate, openJPA, eclipsLink
    * this ORM gives you concrete class of tables of db to interect
    *

        <figure><img src="../.gitbook/assets/image (5).png" alt=""><figcaption></figcaption></figure>

<figure><img src="broken-reference" alt=""><figcaption></figcaption></figure>

* spring data jpa which is on top of ORM

