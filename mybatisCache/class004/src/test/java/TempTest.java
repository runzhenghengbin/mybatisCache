import entity.TempEntity;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

public class TempTest {

    Logger logger = Logger.getLogger(this.getClass());
    @Test
    public  void testSelectAsUpdate() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        sqlSession.update("dao.Temp03Dao.getById", 1);
        sqlSession.update("dao.Temp03Dao.getById", 1);
    }

    @Test
    public  void testCommit() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity1);
        sqlSession.commit();
        TempEntity tempEntity2 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);

    }

    @Test
    public  void testRollback() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity1);
        sqlSession.rollback();
        TempEntity tempEntity2 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);

    }

    @Test
    public  void testForUpdate() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity1);
        sqlSession.update("dao.Temp03Dao.updateById", 1);
        TempEntity tempEntity2 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);

    }

    @Test
    public  void testClearCatch() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity1);
        sqlSession.clearCache();
        TempEntity tempEntity2 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);

    }
    @Test
    public  void test() throws IOException, NoSuchFieldException, IllegalAccessException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp03Dao.getById", 1);
        logger.info(tempEntity1);

        Field executorField = sqlSession.getClass().getDeclaredField("executor");
        executorField.setAccessible(true);
        CachingExecutor  cachingExecutor = (CachingExecutor) executorField.get(sqlSession);

        Field declaredField = cachingExecutor.getClass().getDeclaredField("delegate");
        declaredField.setAccessible(true);
        SimpleExecutor simpleExecutor  = (SimpleExecutor) declaredField.get(cachingExecutor);

        Field localCacheField = simpleExecutor.getClass().getSuperclass().getDeclaredField("localCache");
        localCacheField.setAccessible(true);
        PerpetualCache perpetualCache = (PerpetualCache) localCacheField.get(simpleExecutor);

        Field cacheField = perpetualCache.getClass().getDeclaredField("cache");
        cacheField.setAccessible(true);
        Map<Object,Object> map= (Map<Object, Object>) cacheField.get(perpetualCache);
        logger.info("缓存关闭前");
        for (Map.Entry<Object,Object> objectObjectEntry:map.entrySet()){
            logger.info(objectObjectEntry.getKey() + "===" + objectObjectEntry.getValue());
        }
        sqlSession.close();
        logger.info("缓存关闭后");

        for (Map.Entry<Object,Object> objectObjectEntry:map.entrySet()){
            logger.info(objectObjectEntry.getKey() + "=" + objectObjectEntry.getValue());
        }
    }
}
