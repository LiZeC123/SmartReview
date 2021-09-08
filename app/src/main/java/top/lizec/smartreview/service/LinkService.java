package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Link;
import top.lizec.smartreview.mapper.LinkDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LinkService {

    @Resource
    private LinkDao linkDao;

    @Transactional
    public void insertLink(Integer knowledgeId, String name, String url) {
        linkDao.insert(new Link(knowledgeId, name, url));
    }

    public List<Link> selectKnowledgeLink(Integer kid) {
        return linkDao.selectByMap(Map.of("knowledge_id", kid));
    }
}
