package definition.property.impl;

import definition.property.api.AbstractPropertyDefinition;
import definition.property.api.PropertyType;
import definition.value.generator.api.ValueGenerator;
import definition.value.generator.fixed.FixedValueGenerator;
import definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {

    public static IntegerPropertyDefinition parse(Element propertyElement, String name) {
        Element propValueElement = (Element) propertyElement.getElementsByTagName("PRD-value").item(0);

        ValueGenerator generator;

        Boolean isRandom = propValueElement == null || Boolean.parseBoolean(propValueElement.getAttribute("random-initialize")); // TODO: if no value ask the user
        if (!isRandom) {
            Integer init = Integer.parseInt(propValueElement.getAttribute("init"));
            generator = new FixedValueGenerator(init);
        } else {
            Node rangeNode = propertyElement.getElementsByTagName("PRD-range").item(0);
            if(rangeNode != null) {
                NamedNodeMap rangeAttributes = rangeNode.getAttributes();
                Integer from = Integer.parseInt(rangeAttributes.getNamedItem("from").getNodeValue());
                Integer to = Integer.parseInt(rangeAttributes.getNamedItem("to").getNodeValue());
                generator = new RandomIntegerGenerator(from, to);
            }
            else {
                generator = new RandomIntegerGenerator(-1, -1);
            }
        }
        return new IntegerPropertyDefinition(name, generator);
    }

    public IntegerPropertyDefinition(String name, ValueGenerator<Integer> valueGenerator) {
        super(name, PropertyType.DECIMAL, valueGenerator);
    }

}