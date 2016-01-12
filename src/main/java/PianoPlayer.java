/**
 * Created by al on 10.01.2016.
 */

@Component
public class PianoPlayer extends Person {

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
