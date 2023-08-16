package action.impl;

import action.api.Action;
import definition.entity.EntityDefinition;
import org.w3c.dom.Element;

import java.util.List;

public class ActionParser {
    public static Action parse(Element actionElement, List<EntityDefinition> entities) {
        String actionType = actionElement.getAttribute("type");
        switch (actionType) {
            case "increase": return IncreaseAction.parse(actionElement, entities);
            case "decrease": return DecreaseAction.parse(actionElement, entities);
            case "kill":
                String entityName = actionElement.getAttribute("entity");
                EntityDefinition entityToEffect = entities.stream().filter(entity -> entity.getName().equals(entityName)).findFirst().get();
                return new KillAction(entityToEffect);
            case "condition": return ConditionAction.parse(actionElement, entities);
        }
        throw new RuntimeException("Unknown action");
    }
}
