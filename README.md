# msf4j-maven-plugin

This is a simple maven plugin which can be used to run a MSF4J service with a single command.

You can use the MSF4J archtype to generate a sample MSF4J service. Add the following plugin to build section of your project
```
<plugin>
        <groupId>org.wso2.msf4j</groupId>
        <artifactId>msf4j-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
</plugin>
```

Then once you develop the services. You can execute following command. It will build your service and run the service.
```
mvn msf4j:run
```

## Note
Please note that since this is not avaialble in maven central first you need to get this and locally build the plugin.
To do that just clone or download the repo and do a *mvn clean install*
