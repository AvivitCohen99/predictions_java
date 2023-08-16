package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import execution.context.Context;
import org.w3c.dom.Element;

public class ConditionAction extends AbstractAction {
    protected ConditionAction(ActionType actionType, EntityDefinition entityDefinition) {
        super(actionType, entityDefinition);
    }

    @Override
    public void invoke(Context context) {

    }
}
