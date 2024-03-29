package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import org.w3c.dom.Element;

import javax.swing.text.html.parser.Parser;
import java.util.List;

public class IncreaseAction extends AbstractAction {

    private final String property;
    private final String byExpression;

    public static IncreaseAction parse(Element actionElement, List<EntityDefinition> entities) {
        String entityName = actionElement.getAttribute("entity");
        EntityDefinition entityToEffect = entities.stream().filter(entity -> entity.getName().equals(entityName)).findFirst().get();
        String propertyToEffect = actionElement.getAttribute("property");
        String effectByExpression = actionElement.getAttribute("by");
        return new IncreaseAction(entityToEffect, propertyToEffect, effectByExpression);
    }

    public IncreaseAction(EntityDefinition entityDefinition, String property, String byExpression) {
        super(ActionType.INCREASE, entityDefinition);
        this.property = property;
        this.byExpression = byExpression;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyTYpe(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property);
        }

        Integer v = PropertyType.DECIMAL.convert(propertyInstance.getValue());

        // something that evaluates expression to a number, say the result is 5...
        // now you can also access the environment variables through the active environment...
        // PropertyInstance blaPropertyInstance = activeEnvironment.getProperty("bla");
        int x = 5;

        // actual calculation
        int result = x + v;

        // updating result on the property
        propertyInstance.updateValue(result);
    }

    private boolean verifyNumericPropertyTYpe(PropertyInstance propertyValue) {
        return
                PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
}
