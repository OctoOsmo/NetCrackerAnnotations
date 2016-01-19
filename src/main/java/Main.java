import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by al on 10.01.2016.
 */
public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    private static Person getPerson(String name, Map persons){
        Person person = (Person) persons.get(name);
        person.init();
        return person;
    }

    public static void main(String[] args) {
        Reflections reflections = new Reflections("");

        Map<String, Person> annMap = new HashMap<>();

        log.debug("Annotated classes: ");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> ann : annotated) {
            try {
                annMap.put(ann.getName(), (Person) ann.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.debug(e.getMessage());
            }
        }

        log.debug("annMap contains: ");
        for (Map.Entry<String, Person> personEntry : annMap.entrySet()) {
            log.debug("\t" + personEntry.getKey() + " " + personEntry.getValue().isInited());
        }

        log.debug("Initiating not lazy objects");
        for (Map.Entry<String, Person> personEntry : annMap.entrySet()) {
            try {
                if (personEntry.getValue().getClass().getMethod("init").isAnnotationPresent(Initializer.class)){
                    log.debug("\t\tann present");
                    if (!personEntry.getValue().getClass().getMethod("init").getAnnotation(Initializer.class).lazy()){
                        log.debug("\t\t\tinitiating");
                        personEntry.getValue().init();
                    }
                }
            } catch (NoSuchMethodException e) {
                log.debug(e.getMessage());
            }
        }

        log.debug("annMap contains (after): ");
        for (Map.Entry<String, Person> personEntry : annMap.entrySet()) {
            log.debug("\t" + personEntry.getKey() + " " + personEntry.getValue().isInited());
        }

        log.debug("getting objects and printing they init status");
        for (Map.Entry<String, Person> personEntry : annMap.entrySet()) {
            String key = personEntry.getKey();
            Person person = getPerson(key, annMap);
            log.debug("\t" + key + " " + person.isInited());
        }
    }
}
