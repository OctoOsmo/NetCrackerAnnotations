import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

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

    public static Method getInitializer(final Class<?> type){
        Method init = null;
        Method methods[] = type.getMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(Initializer.class)){
                init = method;
            }
        }
        return init;
    }

    public static void main(String[] args) {
        Reflections reflections = new Reflections("");
        Map<String, Person> annMap = new HashMap<>();

        log.debug("filling map with annotated classes: ");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> ann : annotated) {
            try {
                Person person = (Person) ann.newInstance();
                Method init = getInitializer(ann);
                if (!init.getAnnotation(Initializer.class).lazy()){
                    person.init();
                }
                annMap.put(ann.getName(), person);
            } catch (InstantiationException | IllegalAccessException e) {
                log.debug(e.getMessage());
            }
        }

        log.debug("annMap contains (after lazy initialization): ");
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
