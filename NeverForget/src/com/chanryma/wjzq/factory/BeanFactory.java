package com.chanryma.wjzq.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BeanFactory {
    private static Map<String, Bean> beans = new HashMap<String, Bean>();
    private static Map<String, Object> objects = new HashMap<String, Object>();
    private static BeanFactory beanFactory;

    private BeanFactory(){}

    public static void main(String[] args) {
        System.out.println(BeanFactory.getInstance().getBean("newsService"));
        System.out.println(BeanFactory.getInstance().getBean("caseDao"));
        System.out.println(BeanFactory.getInstance().getBean("newsDao"));
    }

    public static BeanFactory getInstance() {
        if(beanFactory == null) {
            beanFactory = new BeanFactory();
            beanFactory.init();
        }
        return beanFactory;
    }


    public Object getBean(String id) {
        if (beans.containsKey(id)) {
            Bean bean = beans.get(id);
            String scope = bean.getScope();
            if(scope == null || scope.equals("")) {
                scope = "singleton";
            }

            if (scope.equalsIgnoreCase("singleton")) {
                if (objects.containsKey(id)) {
                    return objects.get(id);
                }
            }

            String className = bean.getClassName();
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                Object object = clz.newInstance();

                List<Property> properties = bean.getProperties();
                for (Property property : properties) {
                    String propertyName = property.getName(); //sync

                    String firstChar = propertyName.substring(0, 1);
                    String leaveChar = propertyName.substring(1);
                    String methodName = firstChar.toUpperCase() + leaveChar;
                    //sync -> Sync

                    try {
                        Method[] methods = clz.getMethods(); //execute,isSync,setSync
                        //
                        Method method = null;
                        for (Method methodInClass : methods) {
                            String methodNameInClass = methodInClass.getName();
                            if (methodNameInClass.equals("set" + methodName)) { //setSync
                                method = methodInClass;
                                break;
                            }
                        }
                        // method   => setSync
                        Class<?>[] parmts = method.getParameterTypes(); //拿方法参数的类型
                        // class[0] - > boolean.class
                        String propertyValue = property.getValue();
                        if (parmts[0] == String.class) {
                            method.invoke(object, propertyValue);
                        }
                        if (parmts[0] == int.class) {
                            method.invoke(object, Integer.parseInt(propertyValue));
                        }
                        if (parmts[0] == boolean.class) {
                            method.invoke(object, Boolean.parseBoolean(propertyValue));
                        }
                    } catch(Exception ex) {
                            ex.printStackTrace();
                    }
                }

                if (scope.equalsIgnoreCase("singleton")) {
                    objects.put(id, object);
                }

                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void init() {
        InputStream inputStream = null;
        try {
            inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("bean.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element element = document.getDocumentElement();

            NodeList beanNodes = element.getElementsByTagName("bean");
            if(beanNodes == null) {
                return;
            }
            int beanLength = beanNodes.getLength();
            for(int i = 0; i < beanLength; i++) {
                Element beanElement = (Element) beanNodes.item(i);
                Bean bean = new Bean();
                String id = beanElement.getAttribute("id");
                bean.setId(id);

                String className = beanElement.getAttribute("class");
                bean.setClassName(className);

                String scope = beanElement.getAttribute("scope");
                bean.setScope(scope);

                NodeList propertyNodes = beanElement.getElementsByTagName("property");
                int propertyLength = propertyNodes.getLength();
                for(int j = 0; j < propertyLength; j++) {
                    Element propertyElement = (Element) propertyNodes.item(j);
                    Property property = new Property();
                    String propertyName = propertyElement.getAttribute("name");
                    property.setName(propertyName);

                    String propertyValue = propertyElement.getAttribute("value");
                    property.setValue(propertyValue);

                    bean.addProperty(property);
                }

                beans.put(id, bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
