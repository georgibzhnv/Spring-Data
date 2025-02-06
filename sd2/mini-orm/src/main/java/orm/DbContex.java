package orm;


import com.sun.jdi.InvocationException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContex<E>{
    boolean persist(E entity) throws IllegalAccessException, SQLException;

    List<E> find(Class<E>table, String where)throws SQLException,IllegalAccessException,InstantiationError;
    E findFirst(Class<E>table,String where) throws SQLException, InstantiationError, IllegalAccessException, NoSuchMethodException, InvocationException, InvocationTargetException, InstantiationException;
    E findById(Class<E>table,int id) throws IllegalAccessException, SQLException, InstantiationError, NoSuchMethodException, InvocationException, InvocationTargetException, InstantiationException;

    boolean delete(Class<E> table,int id) throws SQLException;
}
