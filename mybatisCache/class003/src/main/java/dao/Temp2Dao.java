package dao;

import entity.TempEntity;

import java.util.List;

public interface Temp2Dao {

    TempEntity getById1(int id);
    TempEntity getById2(int id);
    List<TempEntity> getList();
}
