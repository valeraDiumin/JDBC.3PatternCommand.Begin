import connectAndCommands.DataBaseManager;
import connectAndCommands.inMemoryDataBaseManager;

/**
 * Created by 123 on 08.03.2016.
 */
public class inMemoryDataBaseManagerTest extends DataBaseManagerTest {
    @Override
    protected DataBaseManager getDataBaseManager() {
        return new inMemoryDataBaseManager();
    }

}
