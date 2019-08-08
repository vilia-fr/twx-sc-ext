package fr.vilia.twx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Just an utility class, super simple, not even testable
 */
public class WindowsServiceParser {

    // Patterns are thread-safe
    private static final Pattern REGEX_SERVICE_NAME = Pattern.compile("SERVICE_NAME: (.+)");
    private static final Pattern REGEX_DISPLAY_NAME = Pattern.compile("DISPLAY_NAME: (.+)");
    private static final Pattern REGEX_STATE =        Pattern.compile("        STATE              : [0-9]+ +(.+) ");
    private static final Pattern REGEX_TYPE =         Pattern.compile("        TYPE               : [0-9]+ +(.+)");

    private static final ProcessBuilder PROCESS_BUILDER = new ProcessBuilder("cmd.exe", "/c", "sc", "query").redirectErrorStream(true);

    public static List<ServiceDefinition> execute() throws IOException {
        List<ServiceDefinition> result = new ArrayList<>();

        Process process = PROCESS_BUILDER.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StateMachine stateMachine = new StateMachine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                ServiceDefinition service = processLine(stateMachine, line);
                if (service != null) {
                    result.add(service);
                }
            }
            if (stateMachine.hasData()) {
                result.add(new ServiceDefinition(stateMachine));
            }
        }

        return result;
    }

    private static ServiceDefinition processLine(StateMachine stateMachine, String line) {
        String serviceName = matchServiceName(line);
        if (serviceName != null) {
            return stateMachine.reset(serviceName);
        }

        String displayName = matchDisplayName(line);
        if (displayName != null) {
            stateMachine.currentDisplayName = displayName;
        }

        String type = matchType(line);
        if (type != null) {
            stateMachine.currentType = type;
        }

        String state = matchState(line);
        if (state != null) {
            stateMachine.currentState = state;
        }

        return null;
    }

    private static String matchServiceName(String line) {
        Matcher m = REGEX_SERVICE_NAME.matcher(line);
        if (m.matches()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    private static String matchDisplayName(String line) {
        Matcher m = REGEX_DISPLAY_NAME.matcher(line);
        if (m.matches()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    private static String matchType(String line) {
        Matcher m = REGEX_TYPE.matcher(line);
        if (m.matches()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    private static String matchState(String line) {
        Matcher m = REGEX_STATE.matcher(line);
        if (m.matches()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    /**
     * For trivial command-line testing
     */
    public static void main(String[] args) throws IOException {
        List<ServiceDefinition> services = execute();
        for (ServiceDefinition s: services) System.out.println(s);
    }

}
