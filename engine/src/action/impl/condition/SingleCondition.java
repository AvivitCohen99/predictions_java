package action.impl.condition;

import action.api.ActionType;
import definition.entity.EntityDefinition;
import org.w3c.dom.Element;

import java.util.List;

public class SingleCondition extends Condition {

        EntityDefinition entity;
        String propertyName;
        String operator;
        String value;

        public static SingleCondition parse(Element conditionElement, List<EntityDefinition> entities) {
                String entityName = conditionElement.getAttribute("entity");
                EntityDefinition entityToEffect = entities.stream().filter(entity -> entity.getName().equals(entityName)).findFirst().get();
                String propertyName = conditionElement.getAttribute("property");
                String operator = conditionElement.getAttribute("operator");
                String value = conditionElement.getAttribute("value");

                return new SingleCondition(entityToEffect, propertyName, operator, value);
        }

        public SingleCondition(EntityDefinition entity, String propertyName, String operator, String value) {
                this.entity = entity;
                this.propertyName = propertyName;
                this.operator = operator;
                this.value = value;
        }


        @Override
        public boolean isFulfilled() {
                return false;
        }
}
