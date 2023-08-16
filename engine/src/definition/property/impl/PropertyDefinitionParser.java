package definition.property.impl;

import definition.property.api.PropertyDefinition;
import org.w3c.dom.Element;

public class PropertyDefinitionParser {
    public static PropertyDefinition parse(Element propertyElement) {
        String type = propertyElement.getAttribute("type");
        String name = propertyElement.getElementsByTagName("PRD-name").item(0).getTextContent();

        if (type.equals("decimal")) {
            return IntegerPropertyDefinition.parse(propertyElement, name);
        }

        throw new RuntimeException("need to implement!"); // TODO: need to implement boolean, string and float
    }
}
