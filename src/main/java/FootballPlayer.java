import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by al on 12.01.2016.
 */
@Component
public class FootballPlayer extends Person {

    private final Logger log = LogManager.getLogger(FootballPlayer.class);

    {
        log.debug("\tFootball");
    }

    boolean initFlag = false;

    @Initializer(lazy = false)
    @Override
    public void init() {
        initFlag = true;
    }

    @Override
    public boolean isInited() {
        return initFlag;
    }
}
