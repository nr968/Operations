package com.project.operations.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class xmlFileWriter {

    private final Logger logger = LogManager.getLogger(xmlFileWriter.class);
    public void xmlWriter(File outputFilePath, String outputFileName, HashMap<Long, Double> results)  {
        logger.info("Creating output file : "+outputFileName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilePath,outputFileName.replaceAll(".xml","_result.xml"))))){
            logger.info("Writing into output file");
            writer.write("<expressions>");
            for (Map.Entry<Long,Double> entry : results.entrySet()){
                String value = Double.toString(entry.getValue());
                String result = value.endsWith(".0") ? value.substring(0,value.length()-2) : value;
                logger.info("ID : "+entry.getKey()+" Result : "+entry.getValue());
                writer.write("<result id=\""+entry.getKey()+"\">"+result+"</result>");
            }
            writer.write("</expressions>");
            logger.info("Results written to output file");
        }catch (IOException ex){
            logger.error("An exception occurred in writing to File {0}",ex);
        }
    }
}
