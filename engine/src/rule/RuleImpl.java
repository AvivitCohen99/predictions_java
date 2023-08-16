package rule;

import action.api.Action;
import action.impl.ActionParser;
import action.impl.ConditionAction;
import action.impl.IncreaseAction;
import action.impl.KillAction;
import definition.entity.EntityDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class RuleImpl implements Rule {

    private final String name;
    private Activation activation;
    private final List<Action> actions;

    public static Rule ruleParser(Element ruleElement, List<EntityDefinition> entities){
        String ruleName = ruleElement.getAttribute("name");
        Activation activation;
        Element activationElement = (Element) ruleElement.getElementsByTagName("PRD-activation").item(0);
        if(activationElement != null) {
            String ticksAttribute = activationElement.getAttribute("ticks");
            Integer activationTicks = !ticksAttribute.isEmpty() ? Integer.parseInt(activationElement.getAttribute("ticks")) : -1;
            if (activationTicks > 0) {
                activation = new ActivationByTicks(activationTicks);
            } else {
                Float activationProbability = Float.parseFloat(activationElement.getAttribute("probability"));
                activation = new ActivationByProbability(activationProbability);
            }
        }
        else {
            activation = new ActivationByTicks(1);
        }
        Rule rule = new RuleImpl(ruleName, activation);
        NodeList actionsList = ruleElement.getElementsByTagName("PRD-action");
        for (int j = 0; j < actionsList.getLength(); j++) {
            Element actionElement = (Element) actionsList.item(j);
            rule.addAction(ActionParser.parse(actionElement, entities));
        }
        return rule;
    }

    public RuleImpl(String name, Activation activation) {
        this.name = name;
        this.activation = activation;
        actions = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Activation getActivation() {
        return activation;
    }

    @Override
    public List<Action> getActionsToPerform() {
        return actions;
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }
}

