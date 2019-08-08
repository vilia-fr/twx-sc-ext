package fr.vilia.twx;

import com.thingworx.types.collections.ValueCollection;

public class ServiceDefinition {
    private final String name;
    private final String description;
    private final String state;
    private final String type;
    public ServiceDefinition(StateMachine sm) {
        this.name = sm.currentServiceName;
        this.description = sm.currentDisplayName;
        this.state = sm.currentState;
        this.type = sm.currentType;
    }
    public ValueCollection toValueCollection() {
        ValueCollection vc = new ValueCollection();
        try {
            vc.SetStringValue("name", name);
            vc.SetStringValue("description", description);
            vc.SetStringValue("type", type);
            vc.SetStringValue("state", state);
        } catch (Exception ignore) {
            // We can safely ignore it here, all values are guaranteed to be well-formatted strings
            ignore.printStackTrace();
        }
        return vc;
    }
    public String toString() {
        return name + " / " + description + " / " + type + " / " + state + ";";
    }
}
