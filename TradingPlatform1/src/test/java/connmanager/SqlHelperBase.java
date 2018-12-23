package connmanager;

import java.sql.*;

public class SqlHelperBase {
  protected Connection con;

  public SqlHelperBase(Connection con) {
    this.con = con;
  }

}
