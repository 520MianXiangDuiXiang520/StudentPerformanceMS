package top.junebao.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import top.junebao.dao.SCDao;
import top.junebao.domain.SC;

import java.util.List;
import java.util.Map;

public class SCDaoTest {

    @Test
    public void selectSCBySnoCnoTest() throws JsonProcessingException {
        System.out.println(SCDao.selectSCBySnoCno("LS-N11", "1"));
    }

    @Test
    public void selectSCBySnoTest() throws JsonProcessingException {
        SCDao.selectSCBySno("LS-N1");
    }

    @Test
    public void selectAllChoiceCId() {
        List<Map<String, Object>> maps = SCDao.selectAllChoiceC("SG-N1");
        System.out.println(maps);
    }
}
