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

    private HashMap<String, Object> objects = new HashMap<>();
    private Map<String, Boolean> objectsStatus = new HashMap<>();

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
        objectsStatus.put(name, false);
    }

    public Object getObject(String name){
        Object obj = objects.get(name);
        List<Method> inits = getInitializers(obj.getClass());
        if (objectsStatus.get(name) == false) {
            initialize(inits, obj, true);
            objectsStatus.put(name, true);
        }
        return obj;
    }
}
