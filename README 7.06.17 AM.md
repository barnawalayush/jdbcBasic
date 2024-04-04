# Statemnt Vs PreparedStatement


Statement	
It is used when SQL query is to be executed only once.
You can not pass parameters at runtime.
Used for CREATE, ALTER, DROP statements.
Performance is very low.
It is base interface.
Used to execute normal SQL queries.
It is used for DDL statements.
No binary protocol is used for communication.

PreparedStatement

It is used when SQL query is to be executed multiple times.
You can pass parameters at runtime.
Used for the queries which are to be executed multiple times.
Performance is better than Statement.
It extends statement interface.
Used to execute dynamic SQL queries.
It is used for any SQL Query.
Binary protocol is used for communication.

In PreparedStatement we can reuse PreparedStatement object but it is not so with Statement object.

Example Code:

### Prepared Statement
```
String sql = "update people set firstname=? , lastname=? where id=?";

PreparedStatement preparedStatement =
        connection.prepareStatement(sql);

preparedStatement.setString(1, "Gary");
preparedStatement.setString(2, "Larson");
preparedStatement.setLong  (3, 123);

int rowsAffected = preparedStatement.executeUpdate();

preparedStatement.setString(1, "Stan");
preparedStatement.setString(2, "Lee");
preparedStatement.setLong  (3, 456);

int rowsAffected = preparedStatement.executeUpdate();
```

### Statement
```
String sql = "select * from people";

ResultSet result = statement.executeQuery(sql);

while(result.next()) {

    String name = result.getString("name");
    long   age  = result.getLong  ("age");

}
```



























