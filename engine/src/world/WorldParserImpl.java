package world;

import action.api.Action;
import action.api.ActionType;
import action.impl.IncreaseAction;
import action.impl.KillAction;
import com.sun.org.apache.xpath.internal.operations.Bool;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.environment.impl.EnvVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import definition.property.impl.IntegerPropertyDefinition;
import definition.value.generator.api.ValueGenerator;
import definition.value.generator.api.ValueGeneratorFactory;
import definition.value.generator.fixed.FixedValueGenerator;
import definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import org.w3c.dom.*;
import rule.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldParserImpl implements WorldParser {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("engine//src//resources//ex1-cigarets.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // parse world
            EnvVariablesManager env = environmentParser((Element) doc.getElementsByTagName("PRD-evironment").item(0));
            ArrayList<EntityDefinition> entities = entitiesParser((Element) doc.getElementsByTagName("PRD-entities").item(0));
            ArrayList<Rule> rules = rulesParser((Element) doc.getElementsByTagName("PRD-rules").item(0), entities);

            World world = new World(env, entities);
            System.out.println(world);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public World parseWorld(String xmlAddress) {
        return null;
    }

    private static EnvVariableManagerImpl environmentParser(Element environment) {
        EnvVariableManagerImpl env = new EnvVariableManagerImpl();
        NodeList envPropertyList = environment.getElementsByTagName("PRD-env-property");
        for (int i = 0; i < envPropertyList.getLength(); i++) {
            Node envPropertyNode = envPropertyList.item(i);
            if (envPropertyNode.getNodeType() == Node.ELEMENT_NODE) {
                Element envPropertyElement = (Element) envPropertyNode;
                String type = envPropertyElement.getAttribute("type");
                String name = envPropertyElement.getElementsByTagName("PRD-name").item(0).getTextContent();

                if (type.equals("decimal")) {
                    NamedNodeMap range = envPropertyElement.getElementsByTagName("PRD-range").item(0).getAttributes();
                    Integer from = Integer.parseInt(range.getNamedItem("from").getNodeValue());
                    Integer to = Integer.parseInt(range.getNamedItem("to").getNodeValue());
                    IntegerPropertyDefinition prop = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger(from, to));
                    env.addEnvironmentVariable(prop);
                }
            }
        }
        return env;
    }

    private static ArrayList<EntityDefinition> entitiesParser(Element entities) {
        NodeList entitiesList = entities.getElementsByTagName("PRD-entity");
        ArrayList<EntityDefinition> entityDefinitionList = new ArrayList();
        for (int i = 0; i < entitiesList.getLength(); i++) {
            Node entityNode = entitiesList.item(i);
            if (entityNode.getNodeType() == Node.ELEMENT_NODE) {
                Element entityElement = (Element) entityNode;
                String name = entityElement.getAttribute("name");
                Integer population = Integer.parseInt(entityElement.getElementsByTagName("PRD-population").item(0).getTextContent());
                EntityDefinitionImpl entityDefinition = new EntityDefinitionImpl(name, population);

                NodeList entitiesPropertiesList = entityElement.getElementsByTagName("PRD-property");
                for (int j = 0; j < entitiesPropertiesList.getLength(); j++) {
                    Node entitiesPropertyNode = entitiesPropertiesList.item(j);
                    if (entitiesPropertyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element entityPropertyElement = (Element) entitiesPropertyNode;
                        String type = entityPropertyElement.getAttribute("type");
                        String propertyName = entityPropertyElement.getElementsByTagName("PRD-name").item(0).getTextContent();

                        if (type.equals("decimal")) {
                            Element propValueElement = (Element) entityPropertyElement.getElementsByTagName("PRD-value").item(0);

                            ValueGenerator generator;

                            Boolean isRandom = Boolean.parseBoolean(propValueElement.getAttribute("random-initialize"));
                            if (!isRandom) {
                                Integer init = Integer.parseInt(propValueElement.getAttribute("init"));
                                generator = new FixedValueGenerator(init);
                            } else {
                                NamedNodeMap range = entityPropertyElement.getElementsByTagName("PRD-range").item(0).getAttributes();
                                Integer from = Integer.parseInt(range.getNamedItem("from").getNodeValue());
                                Integer to = Integer.parseInt(range.getNamedItem("to").getNodeValue());
                                generator = new RandomIntegerGenerator(from, to);
                            }
                            PropertyDefinition prop = new IntegerPropertyDefinition(propertyName, generator);
                            entityDefinition.addEntityProperty(prop);
                        }
                    }
                }
                entityDefinitionList.add(entityDefinition);
            }
        }
        return entityDefinitionList;
    }

    private static ArrayList<Rule> rulesParser(Element rules, List<EntityDefinition> entities) {
        NodeList rulesNodeList = rules.getElementsByTagName("PRD-rule");
        ArrayList<Rule> rulesList = new ArrayList();
        for (int i = 0; i < rulesNodeList.getLength(); i++) {
            Element ruleElement = (Element) rulesNodeList.item(i);
            String ruleName = ruleElement.getAttribute("name");
            Element activationElement = (Element) ruleElement.getElementsByTagName("PRD-activation").item(0);
            Integer activationTicks = Integer.parseInt(activationElement.getAttribute("ticks"));
            Activation activation;
            if (activationTicks > 0) {
                activation = new ActivationByTicks(activationTicks);
            } else {
                Float activationProbability = Float.parseFloat(activationElement.getAttribute("probability"));
                activation = new ActivationByProbability(activationProbability);
            }
            Rule rule = new RuleImpl(ruleName, activation);
            NodeList actionsList = ruleElement.getElementsByTagName("PRD-action");
            for (int j = 0; j < actionsList.getLength(); j++) {
                Element actionElement = (Element) actionsList.item(j);
                String entityName = actionElement.getAttribute("entity");
                String actionType = actionElement.getAttribute("type");
                String propertyToEffect = actionElement.getAttribute("property");
                String effectByExpression = actionElement.getAttribute("by");
                EntityDefinition entityToEffect = entities.stream().filter(entity -> entity.getName().equals(entityName)).findFirst().get();
                Action action;
                switch (actionType) {
                    case "increase":
                        action = new IncreaseAction(entityToEffect, propertyToEffect, effectByExpression);
                        break;
                    case "kill":
                        action = new KillAction(entityToEffect);
                        break;
//                    case "condition":
//                        action = new
//                        break;
                    default: throw new RuntimeException("didnt work");
                }
                rule.addAction(action);
            }
        }
        return rulesList;
    }
}
