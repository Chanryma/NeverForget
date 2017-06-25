package com.chanryma.wjzq.factory;

import java.util.ArrayList;
import java.util.List;

public class Bean {
    private String id;
    private String className;
    private String scope;

    private List<Property> properties= new ArrayList<Property>();

    public List<Property> getProperties() {
        if (properties == null) {
            properties= new ArrayList<Property>();
        }
        return properties;
    }
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }


    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

}
