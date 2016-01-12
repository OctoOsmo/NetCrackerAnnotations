import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by al on 10.01.2016.
 */
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ArrayList<Person> personsList = new ArrayList<>();
        HashMap<String, Person> personsMap = new HashMap<>();
        PianoPlayer p1 = new PianoPlayer();
        Class p1c = p1.getClass();
        if(p1c.isAnnotationPresent(Component.class)){
            personsList.add(p1);
            Component cmp = (Component) p1c.getAnnotation(Component.class);

            log.debug(
                    p1.getClass().getName()
                    + " " + cmp.annotationType().getName());
//                    + " lazy = " + cmp.lazy());
        }
        Reflections reflections = new Reflections("");

        log.debug("All Person classes: ");
        Set<Class<? extends Person>> allClasses =
                reflections.getSubTypesOf(Person.class);
        for (Class<? extends Person> pers : allClasses) {
            log.debug("\t" + pers.getName());
            try {
                personsMap.put(pers.getName(), pers.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("Class missed due error: " + e.getMessage());
            }
        }

        log.debug("Annotated classes: ");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> ann : annotated) {
            log.debug("\t" + ann.getName());
        }


        log.debug("Person map contains: ");
        for (Map.Entry<String, Person> personEntry : personsMap.entrySet()) {
            log.debug("\t" + personEntry.getKey() + " " + personEntry.getValue().isInited());
        }
    }
}
