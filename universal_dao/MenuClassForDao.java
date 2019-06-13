package universal_dao;

import java.sql.Connection;

public class MenuClassForDao extends Dao<Integer, menu_aurora_class.Menu> {
     public MenuClassForDao(Connection connection, String table) {
         super(connection, table);
     }
}
