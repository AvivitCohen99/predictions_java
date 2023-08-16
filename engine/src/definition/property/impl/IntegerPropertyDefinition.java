package definition.property.impl;

import definition.property.api.AbstractPropertyDefinition;
import definition.property.api.PropertyType;
import definition.value.generator.api.ValueGenerator;
import definition.value.generator.fixed.FixedValueGenerator;
import definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;


public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {

    public static IntegerPropertyDefinition parse(Element propertyElement, String name) {
        Element propValueElement = (Element) propertyElement.getElementsByTagName("PRD-value").item(0);

        ValueGenerator generator;

        Boolean isRandom = propValueElement != null ? Boolean.parseBoolean(propValueElement.getAttribute("random-initialize")) : true;
        if (!isRandom) {
            Integer init = Integer.parseInt(propValueElement.getAttribute("init"));
            generator = new FixedValueGenerator(init);
        } else {
            NamedNodeMap range = propertyElement.getElementsByTagName("PRD-range").item(0).getAttributes();
            Integer from = Integer.parseInt(range.getNamedItem("from").getNodeValue());
            Integer to = Integer.parseInt(range.getNamedItem("to").getNodeValue());
            generator = new RandomIntegerGenerator(from, to);
        }
        return new IntegerPropertyDefinition(name, generator);
    }

    public IntegerPropertyDefinition(String name, ValueGenerator<Integer> valueGenerator) {
        super(name, PropertyType.DECIMAL, valueGenerator);
    }

}