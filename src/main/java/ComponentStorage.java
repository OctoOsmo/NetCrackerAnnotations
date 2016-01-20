import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by al on 20.01.2016.
 */
public class ComponentStorage {
    private static final Logger log = LogManager.getLogger(ComponentStorage.class);

    private HashMap<String, Person> persons = new HashMap<>();

    public static List<Method> getInitializers(final Class<?> type){

        List<Method> inits = new ArrayList<>();
        Method methods[] = type.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(Initializer.class)){
                inits.add(method);
            }
        }
        return inits;
    }

    public void logContent(){
        log.debug("Storage content:");
        for (Map.Entry<String, Person> personEntry : persons.entrySet()) {
            String key = personEntry.getKey();
            Person val = getPerson(key);
            log.debug("\t" + key + " is initialized: " + val.isInited());
        }
    }

    private void initialize(List<Method> inits, Person person, boolean lazy){
        for (Method m : inits) {
            if (lazy == m.getAnnotation(Initializer.class).lazy()) {
                try {
                    if (!Modifier.isPublic(m.getModifiers()))
                        m.setAccessible(true);
                    m.invoke(person);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("initialization error" + e.getMessage());
                }
            }
        }
    }

    public void putPerson(String name, Person person){
        List<Method> inits = getInitializers(person.getClass());
        initialize(inits, person, false);
        persons.put(name, person);
    }

    public Person getPerson(String name){
        Person person = (Person) persons.get(name);
        List<Method> inits = getInitializers(person.getClass());
        initialize(inits, person, true);
        return person;
    }
}
