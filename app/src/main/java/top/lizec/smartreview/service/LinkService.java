package top.lizec.smartreview.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Link;
import top.lizec.smartreview.mapper.LinkDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LinkService extends ServiceImpl<LinkDao, Link> {

    @Resource
    private LinkDao linkDao;

    @Transactional
    public void insertLinkBatch(List<Link> links) {
        this.saveBatch(links);
    }

    public List<Link> selectKnowledgeLink(Integer kid) {
        return this.listByMap(Map.of("knowledge_id", kid));
    }
}
