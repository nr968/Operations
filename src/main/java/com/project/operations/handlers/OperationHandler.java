package com.project.operations.handlers;

import com.project.operations.domain.Expression;
import com.project.operations.utility.AttributeUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

public class OperationHandler extends DefaultHandler {

    private final Logger logger = LogManager.getLogger(OperationHandler.class);
    private boolean isStart = false;
    private int itemStartCount = 0;
    private int itemEndCount = 0;
    private int minuendStartCount = 0;
    private int minuendEndCount = 0;
    private int subtrahendStartCount = 0;
    private int subtrahendEndCount = 0;
    private int factorStartCount = 0;
    private int factorEndCount = 0;
    private int dividendStartCount = 0;
    private int dividendEndCount = 0;
    private int divisorStartCount = 0;
    private int divisorEndCount = 0;
    private double finalResult=0.0;
    private double additionResult = 0.0;
    private double subtractionResult = 0.0;
    private double minuend = 0.0;
    private double subtrahend = 0.0;
    private double multiplicationResult = 1.0;
    private double divisionResult = 0.0;
    private double dividend = 0.0;
    private double divisor = 0.0;
    private final HashMap<Long, Double> result = new HashMap<>();
    private StringBuilder data = null;
    private boolean hasCharacterData;
    private boolean bItem;
    private boolean bMinuend;
    private boolean bSubtrahend;
    private boolean bFactor;
    private boolean bDividend;
    private boolean bDivisor;

    private final Expression expression = new Expression();

    private void reInitializeVariables() {
        additionResult = 0.0;
        subtractionResult = 0.0;
        multiplicationResult = 1.0;
        divisionResult = 0.0;
        finalResult = 0.0;
        itemStartCount = 0;
        itemEndCount = 0;
        minuendStartCount = 0;
        minuendEndCount = 0;
        subtrahendStartCount = 0;
        subtrahendEndCount = 0;
        factorStartCount = 0;
        factorEndCount = 0;
        dividendStartCount = 0;
        dividendEndCount = 0;
        divisorStartCount = 0;
        divisorEndCount = 0;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        isStart = true;
        AttributeUtility attributeUtility = new AttributeUtility();
        attributeUtility.checkAttributes(attributes);
        if (attributeUtility.getId() != 0) {
            expression.setId(attributeUtility.getId());
            logger.info("Processing for ID : " + expression.getId() + " Operation : " + qName);
            reInitializeVariables();
        }

        if (qName.equalsIgnoreCase("item")) {
            itemStartCount++;
            bItem = true;
        }
        if (qName.equalsIgnoreCase("minuend")) {
            minuendStartCount++;
            bMinuend = true;
        }
        if (qName.equalsIgnoreCase("subtrahend")) {
            subtrahendStartCount++;
            bSubtrahend = true;
        }
        if (qName.equalsIgnoreCase("factor")) {
            factorStartCount++;
            bFactor = true;
        }
        if (qName.equalsIgnoreCase("dividend")) {
            dividendStartCount++;
            bDividend = true;
        }
        if (qName.equalsIgnoreCase("divisor")) {
            divisorStartCount++;
            bDivisor = true;
        }

        data = new StringBuilder();
        hasCharacterData = false;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        isStart = false;
        if (qName.equalsIgnoreCase("item")) {
            itemEndCount++;
            if (hasCharacterData) {
                if (data.length() > 0) {
                    double value = Double.parseDouble(data.toString());
                    if (bMinuend) {
                        minuend += value;
                    } else if (bSubtrahend) {
                        subtrahend += value;
                    } else if (bDividend) {
                        dividend += value;
                    } else if (bDivisor) {
                        divisor += value;
                    } else {
                        additionResult += value;
                    }
                }
            }
            if (itemStartCount == itemEndCount) bItem = false;
        }

        if (qName.equalsIgnoreCase("minuend")) {
            minuendEndCount++;
            if (hasCharacterData) {
                if (data.length() > 0) minuend = Double.parseDouble(data.toString());
            }
            if (minuendStartCount == minuendEndCount) bMinuend = false;
        }

        if (qName.equalsIgnoreCase("subtrahend")) {
            subtrahendEndCount++;
            if (hasCharacterData) {
                if (data.length() > 0) subtrahend = Double.parseDouble(data.toString());
            }
            if (subtrahendStartCount == subtrahendEndCount) bSubtrahend = false;
        }

        if (qName.equalsIgnoreCase("factor")) {
            factorEndCount++;
            if (hasCharacterData) {
                if (data.length() > 0) {
                    double value = Double.parseDouble(data.toString());
                    if (bMinuend) {
                        minuend *= value;
                    } else if (bSubtrahend) {
                        subtrahend *= value;
                    } else if (bDividend) {
                        dividend *= value;
                    } else if (bDivisor) {
                        divisor *= value;
                    } else {
                        multiplicationResult *= value;
                    }
                }
            }
            if (factorStartCount == factorEndCount) bFactor = false;
        }

        if (qName.equalsIgnoreCase("dividend")) {
            dividendEndCount++;
            if (hasCharacterData) {
                if (data.length() > 0) dividend = Double.parseDouble(data.toString());
            }
            if (dividendStartCount == dividendEndCount) bDividend = false;
        }

        if (qName.equalsIgnoreCase("divisor")) {
            divisorEndCount++;
            if (hasCharacterData) {
                if (data.length() > 0) divisor = Double.parseDouble(data.toString());
            }
            if (divisorStartCount == divisorEndCount) bDivisor = false;
        }

        if (qName.equalsIgnoreCase("addition")) {
            if (bFactor) multiplicationResult *= additionResult;
            else finalResult = additionResult;
            data.setLength(0);
        }

        if (qName.equalsIgnoreCase("subtraction")) {
            subtractionResult = minuend - subtrahend;
            minuend = 0;
            subtrahend = 0;
            if (bItem) additionResult += subtractionResult;
            else if (bMinuend) minuend = subtractionResult;
            else if (bSubtrahend) subtrahend = subtractionResult;
            else if (bFactor) multiplicationResult *= subtractionResult;
            else if (bDividend) dividend = subtractionResult;
            else if (bDivisor) divisor = subtractionResult;
            else finalResult = subtractionResult;
            data.setLength(0);
        }

        if (qName.equalsIgnoreCase("multiplication")) {
            if (bItem) additionResult += multiplicationResult;
            else finalResult = multiplicationResult;
            data.setLength(0);
        }

        if (qName.equalsIgnoreCase("division")) {
            try {
                divisionResult = dividend / divisor;
            }catch (ArithmeticException ex){
                logger.error("Cannot divide by zero{0}",ex);
            }

            dividend = 0;
            divisor = 0;
            if (bItem) additionResult += divisionResult;
            else if (bMinuend) minuend = divisionResult;
            else if (bSubtrahend) subtrahend = divisionResult;
            else if (bFactor) multiplicationResult *= divisionResult;
            else if (bDividend) dividend = divisionResult;
            else if (bDivisor) divisor = divisionResult;
            else finalResult = divisionResult;
            data.setLength(0);
        }

        result.put(expression.getId(), finalResult);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (isStart) data.append(new String(ch, start, length));

        if (!data.toString().isEmpty()) hasCharacterData = true;
    }

    public HashMap<Long, Double> getResult() {
        return result;
    }
}
