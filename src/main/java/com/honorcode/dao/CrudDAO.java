package com.honorcode.dao;

import com.honorcode.exception.DatabaseException;
import java.util.List;

public interface CrudDAO<T> {
    List<T> findAll() throws DatabaseException;
}
