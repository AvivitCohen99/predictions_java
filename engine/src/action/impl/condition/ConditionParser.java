package action.impl.condition;

import definition.entity.EntityDefinition;
import org.w3c.dom.Element;

import java.util.List;

public class ConditionParser {

    public static Condition parse(Element conditionElement, List<EntityDefinition> entities) {
        String singularity = conditionElement.getAttribute("singularity");
        if (singularity.equals("single")) {
            return SingleCondition.parse(conditionElement, entities);
        }
        else if (singularity.equals("multiple")) {
            return MultipleCondition.parse(conditionElement, entities);
        }
    throw new RuntimeException("could not find condition");
    }
}
