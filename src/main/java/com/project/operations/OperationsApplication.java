package com.project.operations;

import com.project.operations.utility.ArgumentValidation;
import com.project.operations.parser.FileParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.operations"})
public class OperationsApplication implements ApplicationRunner {

	private final ArgumentValidation argumentValidation;

	private final FileParser fileParser;

	public OperationsApplication(ArgumentValidation argumentValidation, FileParser fileParser){
		this.argumentValidation = argumentValidation;
		this.fileParser = fileParser;
	}

	public static void main(String[] args) {
		SpringApplication.run(OperationsApplication.class, args);
	}

	public void run(ApplicationArguments arguments) throws IOException {
		if(null==argumentValidation.validateArguments(arguments.getSourceArgs())){
			System.exit(0);
		}else{
			fileParser.parseInputFile(argumentValidation.validateArguments(arguments.getSourceArgs()));
		}
	}
}
