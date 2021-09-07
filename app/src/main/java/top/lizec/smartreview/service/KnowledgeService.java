package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.algo.SentenceClient;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.dto.KnowledgeVO;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.mapper.KnowledgeDao;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {
    private final Map<Integer, Function<KnowledgeDto, List<Knowledge>>> createFunctions;
    @Resource
    SentenceClient sentenceClient;
    @Resource
    private TagService tagService;
    @Resource
    private LinkService linkService;
    @Resource
    private ReviewService reviewService;
    @Resource
    private CheckService checkService;
    @Resource
    private KnowledgeDao knowledgeDao;

    public KnowledgeService() {
        createFunctions = new HashMap<>();
        createFunctions.put(1, this::createEnglishWordBook);
        createFunctions.put(2, this::createLeetCodeNote);
    }


    @Transactional
    public void createKnowledge(Integer userId, KnowledgeDto dto) {
        List<Knowledge> knowledges = createKnowledgeByType(userId, dto);

        knowledges.forEach(knowledgeDao::insert);
        knowledges.forEach(k -> tagService.createKnowledgeTag(dto.getTag(), userId, k.getId()));
        knowledges.forEach(reviewService::createReviewRecord);
    }

    private List<Knowledge> createKnowledgeByType(Integer userId, KnowledgeDto dto) {
        final List<Knowledge> knowledges = createFunctions.get(dto.getAppType()).apply(dto);
        for (Knowledge knowledge : knowledges) {
            knowledge.setCreator(userId);
        }
        return knowledges;
    }

    public List<KnowledgeVO> queryRecentReview(Integer userId) {
        List<Knowledge> knowledges = knowledgeDao.queryRecentReview(userId, LocalDateTime.now());

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
        KnowledgeVO VO = new KnowledgeVO(k);
        VO.setTags(tagService.selectKnowledgeTag(k.getId()));
        VO.setLinks(linkService.selectKnowledgeLink(k.getId()));
        return VO;
    }


    public void deleteKnowledge(Integer userId, Integer kid) {
        checkService.checkKnowledgePermission(userId, kid);
        knowledgeDao.deleteById(kid);
    }


    private List<Knowledge> createEnglishWordBook(KnowledgeDto dto) {
        final List<EnglishWord> englishWords = sentenceClient.queryWordDifficulty(dto.getWords());

        List<Knowledge> knowledges = new ArrayList<>(englishWords.size());
        for (EnglishWord words : englishWords) {
            Knowledge k = new Knowledge();
            k.setAppType(1);
            k.setTitle(words.getWord());
            k.setContent("");
            k.setDifficulty(words.getDifficulty());
            knowledges.add(k);
        }

        return knowledges;
    }

    private List<Knowledge> createLeetCodeNote(KnowledgeDto dto) {
        return new ArrayList<>();
    }
}
