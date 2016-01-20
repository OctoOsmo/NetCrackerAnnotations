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

    private boolean initFlag2 = false;

    @Initializer(lazy = false)
    @Override
    public void init() {
        log.debug("\t\tMiner initializer 1");
        this.initFlag = true;
    }

    @Initializer(lazy = true)
    private void initTwo(){
        log.debug("\t\tMiner initializer 2 (the private one)");
        initFlag2 = true;
    }

    @Override
    public boolean isInited() {
        return (initFlag && initFlag2);
    }
}
