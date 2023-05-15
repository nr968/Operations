package com.project.operations.utility;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ArgumentValidation {

    private final Logger logger = LogManager.getLogger(ArgumentValidation.class);

    public String[] validateArguments(String[] args){
        Options options = new Options();
        Option input = new Option("i","inputFile",true,"Path to the input files");
        options.addOption(input);
        Option output = new Option("o","outputFile",true,"Path to output files");
        options.addOption(output);
        CommandLineParser commandLineParser = new DefaultParser();
        try{
            CommandLine cmd = commandLineParser.parse(options,args);
            if (cmd.hasOption("i") && cmd.hasOption("o"))
                return new String[]{cmd.getOptionValue("i"), cmd.getOptionValue("o")};
        } catch (ParseException e) {
            logger.error("Error in parsing arguments{0}",e);
        }
        return null;
    }
}
