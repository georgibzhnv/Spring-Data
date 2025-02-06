package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContex<E>{
    private static final String SELECT_STAR_FROM = "SELECT * FROM ";
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUE (%s) ; ";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s ;";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE %s ;";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(Object entity) throws IllegalAccessException, SQLException {
        Field primary = getIdField(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        return (value != null && (int)value > 0) ?
                doUpdate(entity, primary) :
                doInsert(entity,primary);
    }


    @Override
    public List<E> find(Class<E> table, String where) throws SQLException, IllegalAccessException, InstantiationError {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Statement statement = connection.createStatement();
        String query = SELECT_STAR_FROM + getTableName(table)+
                (where.equals("")?"" : " "+ where + " LIMIT 1;");

        ResultSet resultSet = statement.executeQuery(query);
        E entity = table.getConstructor().newInstance();
        resultSet.next();
        this.fillEntity(table,resultSet,entity);
        return entity;
    }

    @Override
    public E findById(Class<E>table, int id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return findFirst(table,"WHERE id= "+id);
    }

    @Override
    public boolean delete(Class<E> table,int id)throws SQLException{
        String query = String.format(DELETE_QUERY,getTableName(table),"id = "+id);

        return executeQuery(query);
    }

//    @Override
//    private Field getId(Class entity){
//        return Arrays.stream(entity.getDeclaredFields())
//                .filter(x->x.isAnnotationPresent(Id.class))
//                .findFirst()
//                .orElseThrow(()-> new UnsupportedOperationException("Entity does not have primary key."));
//    }

    //Utility methods
    private boolean doUpdate(Object entity, Field primaryKey) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        Function<Field,String> getFieldNameAndValue = (Field x) ->{
                x.setAccessible(true);
                try {
                    return String.format(" %s = %s",
                            x.isAnnotationPresent(Id.class)
                                    ? "id" : x.getAnnotation(Column.class).name(),
                            x.getType() == String.class || x.getType() == LocalDate.class
                                    ? "'" + x.get(entity) + "'"
                                    : x.get(entity).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return "";
            };
        List<String>setFieldNameAndValues = Arrays.stream(entity.getClass().getDeclaredFields())
                .map(getFieldNameAndValue)
                .collect(Collectors.toList());

        String insertQuery = String.format(UPDATE_QUERY,tableName,String.join(", ",setFieldNameAndValues),
                " id = " + primaryKey.get(entity));
        return executeQuery(insertQuery);
    }



    private boolean doInsert(Object entity, Field primary) throws SQLException {
        String tableName = getTableName(entity.getClass());
        String fieldNamesStr = getFieldNames(entity).stream().collect(Collectors.joining(", "));
        String fieldValuesStr = getFieldValues(entity).stream().collect(Collectors.joining(", "));
        String insertQuery = String.format(INSERT_QUERY,tableName,fieldNamesStr,fieldValuesStr);
        return executeQuery(insertQuery);
    }

    private boolean executeQuery(String sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.execute();
    }

    private Field getIdField(Class entity){
       return Arrays.stream(entity.getDeclaredFields())
                .filter(field ->field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(()-> new UnsupportedOperationException("Entity does not have a primary key."));
    }

    private String getTableName(Class<?> entity){
        Entity entityAnnotation = entity.getAnnotation(Entity.class);
        if (entityAnnotation !=null  && entityAnnotation.name().length()>0){
            return entityAnnotation.name();
        }else{
            return entity.getSimpleName();
        }
    }
    private List<String> getFieldValues(Object entity) {
        Function<Field,String> getFieldValue = field -> {
            field.setAccessible(true);
            try {
                Object value =  field.get(entity);
                return field.getType() == String.class || field.getType()== LocalDate.class ?
                        String.format("'%s'",value.toString()) :
                        value.toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return "";
        };
       return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(getFieldValue)
                .collect(Collectors.toList());
    }

    private List<String> getFieldNames(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    return field.getAnnotation(Column.class).name();
                })
                .collect(Collectors.toList());
    }
    private void fillEntity(Class<E>table,ResultSet resultSet,E entity) throws SQLException, IllegalAccessException {

        Field[] declaredFields = table.getDeclaredFields();
        for (Field field : declaredFields){
            field.setAccessible(true);

            fillField(field,entity,resultSet,
                    field.isAnnotationPresent(Id.class)
                            ? "id" : field.getAnnotation(Column.class).name());
        }
    }

    private void fillField(Field field, E entity, ResultSet resultSet, String name) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        switch (name){
            case "id":field.set(entity,resultSet.getInt("id"));break;
            case "username":field.set(entity,resultSet.getString("username"));break;
            case "password":field.set(entity,resultSet.getString("password"));break;
            case "age":field.set(entity,resultSet.getInt("age"));break;
            case "registrationDate":field.set(entity,resultSet.getString("registration_date"));break;
        }
    }
}
