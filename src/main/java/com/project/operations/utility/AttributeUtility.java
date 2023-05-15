package com.project.operations.utility;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;

@Component
public class AttributeUtility {

    private long id = 0L;

    public long getId() {
        return id;
    }

    public void checkAttributes(Attributes attributes){
        if(attributes.getLength()==2){
            id = Long.parseLong(attributes.getValue("id"));
        }else if(attributes.getLength()==1){
            id = Long.parseLong(attributes.getValue("id"));
        }
    }
}
