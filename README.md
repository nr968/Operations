# Operations

## Description
This is an application which calculates top-level operations from input files and store the result in output files in XML format.

## How To Build
1. Open an IDE and import `pom.xml` as project.
2. Run `mvn install` to install dependencies.
3. Run `mvn clean package` to create the JAR for the application.

## Usage
1. Launch terminal
2. Run command - `java -jar jar_name.jar -i [inputFilesPath] -o [outputFilesPath]`
3. Example - `java -jar operations-0.0.1-SNAPSHOT.jar -i "C:\operations\input" -o "C:\operations\output"`
4. Output Files will be generated in the mentioned output path

