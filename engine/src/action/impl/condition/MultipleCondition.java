package action.impl.condition;

import action.impl.ActionParser;
import definition.entity.EntityDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class MultipleCondition extends Condition {
    String logical;
    List<Condition> innerConditions;

    public static MultipleCondition parse(Element conditionElement, List<EntityDefinition> entities) {
        List<Condition> innerConditions = new ArrayList();
        String logical = conditionElement.getAttribute("logical");
        NodeList innerConditionsList = conditionElement.getElementsByTagName("PRD-condition");
        for (int j = 0; j < innerConditionsList.getLength(); j++) {
            Element innerConditionElement = (Element) innerConditionsList.item(j);
            innerConditions.add(ConditionParser.parse(innerConditionElement, entities));
        }
        return new MultipleCondition(logical, innerConditions);
    }

    public MultipleCondition (String logical, List<Condition> innerConditions) {
        this.logical = logical;
        this.innerConditions = innerConditions;
    }

    @Override
    public boolean isFulfilled() {
        if (logical.equals("and")) {
            return innerConditions.stream().allMatch(Condition::isFulfilled);
        } else
            return innerConditions.stream().anyMatch(Condition::isFulfilled);
    }
}
