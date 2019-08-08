package fr.vilia.twx;

public class StateMachine {
    public String currentServiceName;
    public String currentDisplayName;
    public String currentState;
    public String currentType;

    /**
     * @return Returns last service definition, or null if it's the first one
     */
    public ServiceDefinition reset(String serviceName) {
        ServiceDefinition result = hasData() ? new ServiceDefinition(this) : null;
        currentServiceName = serviceName;
        currentState = null;
        currentType = null;
        currentDisplayName = null;
        return result;
    }

    public boolean hasData() {
        return currentServiceName != null;
    }
}
