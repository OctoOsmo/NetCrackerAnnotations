import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by al on 20.01.2016.
 */
@Component
public class Miner extends Person {

    private final Logger log = LogManager.getLogger(Miner.class);

    {
        log.debug("\tMiner");
    }

    @Initializer(lazy = true)
    @Override
    public void init() {
        this.initFlag = true;
    }
}
