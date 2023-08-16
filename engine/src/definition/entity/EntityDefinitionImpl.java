package definition.entity;

import definition.property.api.PropertyDefinition;
import definition.property.impl.PropertyDefinitionParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition {

    private final String name;
    private final int population;
    private final List<PropertyDefinition> properties;

    public static EntityDefinition entityParser(Element entityElement){
        String name = entityElement.getAttribute("name");
        Integer population = Integer.parseInt(entityElement.getElementsByTagName("PRD-population").item(0).getTextContent());
        EntityDefinitionImpl entityDefinition = new EntityDefinitionImpl(name, population);

        NodeList entitiesPropertiesList = entityElement.getElementsByTagName("PRD-property");
        for (int j = 0; j < entitiesPropertiesList.getLength(); j++) {
            Node entitiesPropertyNode = entitiesPropertiesList.item(j);
            if (entitiesPropertyNode.getNodeType() == Node.ELEMENT_NODE) {
                Element entityPropertyElement = (Element) entitiesPropertyNode;
                entityDefinition.addEntityProperty(PropertyDefinitionParser.parse(entityPropertyElement));
            }
        }
        return entityDefinition;
    }

    public EntityDefinitionImpl(String name, int population) {
        this.name = name;
        this.population = population;
        properties = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }

    @Override
    public void addEntityProperty(PropertyDefinition propertyDefinition){
        this.properties.add(propertyDefinition);
    }
}
