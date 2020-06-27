package dao;


import entity.TempEntity;

public interface Temp03Dao {

    TempEntity getById(int id);

    void updateById(int id);
}
