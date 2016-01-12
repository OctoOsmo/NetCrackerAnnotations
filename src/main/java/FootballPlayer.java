/**
 * Created by al on 12.01.2016.
 */
@Component
public class FootballPlayer extends Person {

    boolean initFlag = false;

    @Initializer(lazy = false)
    @Override
    public void init() {
        initFlag = true;
    }
}
