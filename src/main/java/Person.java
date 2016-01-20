import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by al on 10.01.2016.
 */
public abstract class Person {

    private final Logger log = LogManager.getLogger(Person.class);

    {
        log.debug("Person: новый экземпляр");
    }

    protected boolean initFlag = false;

    public boolean isInited() {
        return initFlag;
    }

    public void init() {
        log.debug("\t\t" + this.getClass() + "initializer");
        initFlag = true;
    }

}
