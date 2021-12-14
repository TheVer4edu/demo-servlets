package com.example.demoservlets.orm;

import java.util.List;

public interface UsersDAO {
    User get(long id);
    List<User> getAll();
    void add(User dataSet);
}
