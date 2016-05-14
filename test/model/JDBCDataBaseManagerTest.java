package model;

import connectAndCommands.DataBaseManager;
import connectAndCommands.JDBCDataBaseManager;

/**
 * Created by 123 on 08.03.2016.
 */
public class JDBCDataBaseManagerTest extends DataBaseManagerTest {

    @Override
    protected DataBaseManager getDataBaseManager() {
        return new JDBCDataBaseManager();
    }

}