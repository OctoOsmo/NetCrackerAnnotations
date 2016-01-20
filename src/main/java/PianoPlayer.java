import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by al on 10.01.2016.
 */

@Component
public class PianoPlayer extends Person {

    private final Logger log = LogManager.getLogger(PianoPlayer.class);

    {
        log.debug("\tPianoPlayer");
    }

    public void pressPedal(int pedalId){

    }

    public void pressKey(int keyId){

    }

    public void playMelody(){

    }

    @Override
    @Initializer(lazy = true)
    public void init() {
        initFlag = true;
    }
}
