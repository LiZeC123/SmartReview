package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.dto.KnowledgeVO;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.mapper.KnowledgeDao;
import top.lizec.smartreview.service.app.CreateBaseKnowledgeService;
import top.lizec.smartreview.service.app.CreateEnglishWordNoteService;
import top.lizec.smartreview.service.app.CreateKnowledgeService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {
    private Map<Integer, CreateKnowledgeService> createServices;

    @Resource
    private TagService tagService;
    @Resource
    private LinkService linkService;
    @Resource
    private SentenceService sentenceService;
    @Resource
    private ReviewService reviewService;
    @Resource
    private CheckService checkService;
    @Resource
    private KnowledgeDao knowledgeDao;

    @Resource
    private CreateEnglishWordNoteService createEnglishWordNoteService;
    @Resource
    private CreateBaseKnowledgeService createBaseKnowledgeService;


    @PostConstruct
    public void init() {
        createServices = new HashMap<>();
        createServices.put(1, createBaseKnowledgeService);
        createServices.put(2, createEnglishWordNoteService);
    }


    @Transactional
    public void createKnowledge(Integer userId, KnowledgeDto dto) {
        final CreateKnowledgeService createKnowledgeService = createServices.get(dto.getAppType());
        List<Knowledge> knowledges = createKnowledgeService.parseKnowledgeDto(dto);

        for (Knowledge k : knowledges) {
            k.setCreator(userId);

            boolean isNewKnowledge = insertNewKnowledge(k);
            createKnowledgeService.afterInsertKnowledge(k, dto);
            if (isNewKnowledge) {
                tagService.createKnowledgeTag(dto.getTag(), userId, k.getId());
                reviewService.createReviewRecord(k);
            }
        }
    }

    /**
     * 如果知识点不存在则插入知识点, 否则不进行插入. 无论是否插入都更新知识点ID.
     *
     * @param k 待插入知识点
     * @return 知识点是否已经存在
     */
    private boolean insertNewKnowledge(Knowledge k) {
        // 数据不存在则插入, 否则返回数据库中的实体
        List<Knowledge> knowledges = knowledgeDao.selectByMap(Map.of("title", k.getTitle()));
        if (knowledges.isEmpty()) {
            knowledgeDao.insert(k);
            return true;
        } else {
            Integer kid = knowledges.get(0).getId();
            k.setId(kid);
            return false;
        }
    }

    public List<KnowledgeVO> queryRecentReview(Integer userId) {
        List<Knowledge> knowledges = knowledgeDao.queryRecentReview(userId, new Date());

        return completeKnowledge(knowledges);
    }

    public List<KnowledgeVO> queryList(Integer userId) {
        List<Knowledge> knowledges = knowledgeDao.queryList(userId);
        return completeKnowledge(knowledges);
    }

    public KnowledgeVO queryDetail(Integer userId, Integer kid) {
        checkService.checkKnowledgePermission(userId, kid);
        final Knowledge knowledge = knowledgeDao.selectById(kid);
        return completeKnowledge(knowledge);
    }

    private List<KnowledgeVO> completeKnowledge(List<Knowledge> knowledges) {
        return knowledges.stream().map(this::completeKnowledge).collect(Collectors.toList());
    }

    private KnowledgeVO completeKnowledge(Knowledge k) {
        Integer kid = k.getId();
        KnowledgeVO VO = new KnowledgeVO(k);
        VO.setTags(tagService.selectKnowledgeTag(kid));
        VO.setLinks(linkService.selectKnowledgeLink(kid));
        VO.setSentences(sentenceService.selectSentenceById(kid));
        return VO;
    }


    public void deleteKnowledge(Integer userId, Integer kid) {
        checkService.checkKnowledgePermission(userId, kid);
        knowledgeDao.deleteById(kid);
    }
}
