package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.impl.condition.Condition;
import action.impl.condition.ConditionParser;
import definition.entity.EntityDefinition;
import execution.context.Context;
import org.w3c.dom.Element;

import java.util.List;

public class ConditionAction extends AbstractAction {
    Condition condition;
    Action thenAction;
    Action elseAction;

    public static ConditionAction parse(Element actionElement, List<EntityDefinition> entities) {
        String entityName = actionElement.getAttribute("entity");
        EntityDefinition entityToEffect = entities.stream().filter(entity -> entity.getName().equals(entityName)).findFirst().get();
        Element conditionElement = (Element) actionElement.getElementsByTagName("PRD-condition").item(0);
        Element thenElement = (Element) actionElement.getElementsByTagName("PRD-then").item(0);
        Element elseElement = (Element) actionElement.getElementsByTagName("PRD-else").item(0);

        Condition condition = ConditionParser.parse(conditionElement, entities);
        Action thenAction = ActionParser.parse((Element) thenElement.getElementsByTagName("PRD-action").item(0), entities);
        Action elseAction = elseElement != null ? ActionParser.parse((Element) elseElement.getElementsByTagName("PRD-action").item(0), entities) : null;
        return new ConditionAction(ActionType.CONDITION, entityToEffect, condition, thenAction, elseAction);
    }

    protected ConditionAction(ActionType actionType, EntityDefinition entityDefinition, Condition condition, Action thenAction, Action elseAction) {
        super(actionType, entityDefinition);
        this.condition = condition;
        this.thenAction = thenAction;
        this.elseAction = elseAction;
    }

    @Override
    public void invoke(Context context) {

    }
}
