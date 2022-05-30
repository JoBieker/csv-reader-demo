# Getting Started
Diese Implementierung zeigt, wie man mit Spring Batch:
+ Aus einem Verzeichnis alle Dateien einliest, die auf '.csv' enden.
  + Voraussetzung ist, dass die Dateien alle die gleiche Struktur haben
+ Die Header der CSV-Dateien werden über die Attribute der Eigenschaften 
  'nrw.bieker.batch.inputFields' und 'nrw.bieker.batch.outputFields' als kommaseparierte Listen der 
    Form feld1, feld2, feld3 eingetragen.
+ Es müssen in den beiden Pojo InputZeile und OutputZeile die entsprechenden Felder als Klassenvariablen 
  eingetragen werden. Dank lombok ist mehr nicht nötig.
+ Der Processor ist noch als Lambda in der einzigen @Configuration-Klasse ausgeführt. 
  Aber er könnte auch leicht in einer eigenen Klasse ausprogrammiert werden, wenn man etwa auch Prüfung gegen  
  eine Datenbank machen wollte.


### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#howto-batch-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

