import entity.TempEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TempTest {

    Logger logger = Logger.getLogger(this.getClass());
    @Test
    public  void test() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp2Dao.getById1", 1);
        logger.info(tempEntity1);
        TempEntity tempEntity2 = sqlSession.selectOne("dao.Temp2Dao.getById2", 1);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);
    }

    @Test
    public  void testParam() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.Temp2Dao.getById1", 1);
        logger.info(tempEntity1);
        TempEntity tempEntity2 = sqlSession.selectOne("dao.Temp2Dao.getById1", 2);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);
    }

    @Test
    public  void testPage() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream,"dev");
        SqlSession sqlSession = build.openSession();
        RowBounds rowBounds1 = new RowBounds(0,1);
        List<TempEntity> tempEntity1 = sqlSession.selectList("dao.Temp2Dao.getList", null,rowBounds1);
        logger.info(tempEntity1);
        RowBounds rowBounds2 = new RowBounds(0,2);
        List<TempEntity> tempEntity2 = sqlSession.selectList("dao.Temp2Dao.getList",null, rowBounds2);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);
    }
}
