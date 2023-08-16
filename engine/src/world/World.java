package world;

import definition.entity.EntityDefinition;
import definition.environment.api.EnvVariablesManager;
import rule.Rule;
import termination.Termination;

import java.util.List;


public class World {

    EnvVariablesManager env;
    List<EntityDefinition> entities;
    List<Rule> rules;
    Termination termination;

    public World() {
    }
    public World(EnvVariablesManager env , List<EntityDefinition> entities, List<Rule> rules, Termination termination){
        this.env = env;
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
    }

    public EnvVariablesManager getEnv() {
        return env;
    }

    public List<EntityDefinition> getEntities() {
        return entities;
    }

//    public void setRules(RuleImpl rules[]) {
//        this.rules = rules;
//    }
//
    public List<Rule> getRules() {
        return rules;
    }
}


