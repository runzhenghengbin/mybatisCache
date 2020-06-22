import entity.TempEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class TempTest {

    Logger logger = Logger.getLogger(this.getClass());
    @Test
    public  void test() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession();
        TempEntity tempEntity1 = sqlSession.selectOne("dao.TempDao.getById", 1);
        logger.info(tempEntity1);
        TempEntity tempEntity2 = sqlSession.selectOne("dao.TempDao.getById", 1);
        logger.info(tempEntity2);
        logger.info(tempEntity1 == tempEntity2);
    }
}
