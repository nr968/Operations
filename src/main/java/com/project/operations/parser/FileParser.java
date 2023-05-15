package com.project.operations.parser;

import com.project.operations.handlers.OperationHandler;
import com.project.operations.writer.xmlFileWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

@Component
public class FileParser {

    private final Logger logger = LogManager.getLogger(FileParser.class);

    @Autowired
    private xmlFileWriter writer;

    public void parseInputFile(String[] args) throws IOException {
        String inputFilesPath = args[0];
        String outputFilesPath = args[1];
        logger.info("Input Files Path : "+inputFilesPath);
        logger.info("Output Files Path : "+outputFilesPath);
        File inputDirectory = new File(inputFilesPath);
        File outputDirectory = new File(outputFilesPath);
        File[] inputFiles = inputDirectory.listFiles((dir,name)->name.endsWith(".xml"));
        assert inputFiles != null;
        for (File inputFile:inputFiles) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            try{
                logger.info("Current Parsing File : "+inputFile);
                SAXParser saxParser = saxParserFactory.newSAXParser();
                OperationHandler operationHandler = new OperationHandler();
                logger.info("Starting parsing");
                saxParser.parse(inputFile, operationHandler);
                logger.info("Parsing ended");
                String outputFileName = inputFile.getName();
                writer.xmlWriter(outputDirectory, outputFileName,operationHandler.getResult());
            } catch (ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
        System.exit(0);
    }
}
