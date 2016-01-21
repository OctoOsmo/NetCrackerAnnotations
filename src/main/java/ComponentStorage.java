import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by al on 20.01.2016.
 */
public class ComponentStorage {
    private static final Logger log = LogManager.getLogger(ComponentStorage.class);

    private HashMap<String, Object> objects = new HashMap<>();
    private Set<String > objectsStatusSet = new HashSet<>();

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

    public void logPersons(){
        log.debug("\nStorage content (only persons):");
        for (Map.Entry<String, Object> personEntry : objects.entrySet()) {
            if (personEntry.getValue() instanceof Person){ // necessary to call isInited()
                String key = personEntry.getKey();
                Object val = getObject(key);
                Person p = (Person) val;
                log.debug("\t" + key + " is initialized: " + p.isInited());
            }
        }
    }

    private void initialize(List<Method> inits, Object obj, boolean lazy){
        for (Method m : inits) {
            if (lazy == m.getAnnotation(Initializer.class).lazy()) {
                try {
                    if (!Modifier.isPublic(m.getModifiers()))
                        m.setAccessible(true);
                    m.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("initialization error" + e.getMessage());
                }
            }
        }
    }

    public void putObject(String name, Object obj){
        List<Method> inits = getInitializers(obj.getClass());
        initialize(inits, obj, false);
        objects.put(name, obj);
    }

    public Object getObject(String name){
        Object obj = objects.get(name);
        List<Method> inits = getInitializers(obj.getClass());
        if(!objectsStatusSet.contains(name)){
            initialize(inits, obj, true);
            objectsStatusSet.add(name);
        }
        return obj;
    }
}
