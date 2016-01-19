import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by al on 12.01.2016.
 */
public class Pilot extends Person {

    private final Logger log = LogManager.getLogger(Pilot.class);

    {
        log.debug("\tPilot");
    }

    private String name = "Joe";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
