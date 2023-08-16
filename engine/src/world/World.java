package world;

import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.environment.impl.EnvVariableManagerImpl;
import rule.Rule;
import rule.RuleImpl;

import java.util.ArrayList;


public class World {

    EnvVariablesManager env;

    ArrayList<EntityDefinition> entities;
    ArrayList<Rule> rules;
//    Termination termination;

    public World() {
    }
    public World(EnvVariablesManager env , ArrayList<EntityDefinition> entities){//, RuleImpl[] rules) {      //Termination termination;
        this.env = env;
        this.entities = entities;
//        this.rules = rules;
        //    Termination termination;
    }

    public EnvVariablesManager getEnv() {
        return env;
    }

    public ArrayList<EntityDefinition> getEntities() {
        return entities;
    }

//    public void setRules(RuleImpl rules[]) {
//        this.rules = rules;
//    }
//
//    public Rule[] getRules() {
//        return rules;
//    }
}


