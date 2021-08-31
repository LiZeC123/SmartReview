package top.lizec.smartreview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import top.lizec.smartreview.algo.SentenceClient;
import top.lizec.smartreview.mapper.KnowledgeDao;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class SmartReviewApplicationTests {



    @Resource
    KnowledgeDao knowledgeDao;

    @Resource
    SentenceClient sentenceClient;

    @Test
    void contextLoads() {
    }

    @Test
    void databaseBaseTest() {
        //knowledgeDao.
    }

    @Test
    void testSentenceClient() {
        //sentenceClient.sentenceToWord("Bajadasaurus is a genus of sauropod dinosaur of northern Patagonia, Argentina, from around 145 to 133 million years ago during the Early Cretaceous epoch. ");
    }

}
