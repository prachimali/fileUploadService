

INTRODUCTION
------------

This project can be used to transfer file on server and save file metadata in database.


REQUIREMENTS
------------

JDK 1.8
Apache Tomcat 8.0
Oracle 11g
Maven 
TestNG


CONTENTS
---------

/readme.txt - this file
/pom.xml    - maven configuration file
/src/main/java - java classes for rest service
/src/test/java - java classes tests
/src/main/resources/application.properties - file database configuration data
/src/main/resources/paths.properties - file to define destination file path

RUNNING THE SERVICE
-------------------

1) Install Eclipse and import the project.
2) Install Maven 
3) Install SQLDeveloper.
4) create table in database using below query.
   
   create table file_details(FILE_ID NUMBER, FILE_NAME VARCHAR2(200), FILE_SIZE NUMBER, FILE_CONTENT_TYPE VARCHAR2(32), CREATE_DATE TIMESTAMP WITH TIME ZONE, PRIMARY KEY(FILE_ID));

5) Go into project directory from cmd and run below commands.

   mvn clean install -Ddestination.file.path=<<enter_your_path_here>>
   mvn spring-boot:run
   mvn spring-boot:run -Ddestination.file.path=<<enter_your_path_here>>

6) call the service from postman.
   Method : POST
   URI : http://localhost:8098/file/upload
   Param : file (attach the file)

7) check the response and check the entry in file_details table in database.





