package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Link;
import top.lizec.smartreview.mapper.LinkDao;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkService {

    @Resource
    private LinkDao linkDao;

    @Transactional
    public void createLink(Integer knowledgeId, String name, String url) {
        linkDao.insert(new Link(knowledgeId, name, url));
    }

    public List<Link> selectKnowledgeLink(Integer kid) {
        return linkDao.selectKnowledgeLink(kid);
    }
}
