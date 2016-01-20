import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by al on 12.01.2016.
 */
@Component
public class FootballPlayer extends Person {

    private final Logger log = LogManager.getLogger(FootballPlayer.class);

    {
        log.debug("\tFootball player");
    }

    @Initializer
    @Override
    public void init() {
        log.debug("\t\tFootball player initializer");
        initFlag = true;
    }

}
