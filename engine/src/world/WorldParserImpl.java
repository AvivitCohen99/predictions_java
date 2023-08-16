package world;

import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.environment.impl.EnvVariableManagerImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rule.Rule;
import rule.RuleImpl;
import termination.Termination;

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
            EnvVariablesManager env = EnvVariableManagerImpl.environmentParser((Element) doc.getElementsByTagName("PRD-evironment").item(0));
            ArrayList<EntityDefinition> entities = entitiesParser((Element) doc.getElementsByTagName("PRD-entities").item(0));
            ArrayList<Rule> rules = rulesParser((Element) doc.getElementsByTagName("PRD-rules").item(0), entities);
            Termination termination = Termination.parse((Element) doc.getElementsByTagName("PRD-termination").item(0));

            World world = new World(env, entities, rules, termination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public World parseWorld(String xmlAddress) {
        return null;
    }

    private static ArrayList<EntityDefinition> entitiesParser(Element entities) {
        NodeList entitiesList = entities.getElementsByTagName("PRD-entity");
        ArrayList<EntityDefinition> entityDefinitionList = new ArrayList();
        for (int i = 0; i < entitiesList.getLength(); i++) {
            Node entityNode = entitiesList.item(i);
            Element entityElement = (Element) entityNode;
            entityDefinitionList.add(EntityDefinitionImpl.entityParser(entityElement));
        }
        return entityDefinitionList;
    }

    private static ArrayList<Rule> rulesParser(Element rules, List<EntityDefinition> entities) {
        NodeList rulesNodeList = rules.getElementsByTagName("PRD-rule");
        ArrayList<Rule> rulesList = new ArrayList();
        for (int i = 0; i < rulesNodeList.getLength(); i++) {
            Element ruleElement = (Element) rulesNodeList.item(i);
            rulesList.add(RuleImpl.ruleParser(ruleElement, entities));
        }
        return rulesList;
    }
}
