# Prosperad

#Installation steps:

<b>Required software:</b>
<br/>
-Maven
<br/>
-JBoss Wildfly 10
<br/>
-MySQL Database
<br/>
-Eclipse Neon (suggested IDE)
<br/>
-MySQL Workbench (suggested)
<br/>
<br/>
<b>Setup</b>
<br/>
-Is required to create a datasource "java:jboss/datasources/JSalesDS" in the Wildfly Server to be accesed via JNDI, and add the following parameters to the connection chain: "useUnicode=yes&characterEncoding=UTF-8"
<br/>
-Setup the DB schema definition (mysql-db-schema.mwb) under the directory "db-schema"
<br/>
-Run the domain loads under: "domain-loads" directory
<br/>
-Import the Eclipse project into the IDE using the option import as "Existing Maven Project"
<br/>
-Set UTF-8 as default servlets encoding in the subsystems module
