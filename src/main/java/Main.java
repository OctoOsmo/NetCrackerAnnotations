import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.*;

/**
 * Created by al on 10.01.2016.
 */
public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Reflections reflections = new Reflections("");
        ComponentStorage storage = new ComponentStorage();

        log.debug("Filling map with annotated classes: ");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> ann : annotated) {
            try {
                storage.putObject(ann.getName(), ann.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }

        storage.logPersons();
    }
}
