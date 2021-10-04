package top.lizec.smartreview.algo;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.algo.entity.WordTag;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SentenceAnalyzer {
    Map<String, EnumSet<WordTag>> glossary;

    public SentenceAnalyzer() throws IOException {
        glossary = new HashMap<>();

        ClassPathResource classPathResource = new ClassPathResource("dataset/Glossary.cvs");

        try (Scanner in = new Scanner(classPathResource.getInputStream())) {
            while (in.hasNext()) {
                String line = in.nextLine();
                final String[] tokens = line.split(",");
                String key = tokens[0].strip();
                final String[] tags = tokens[1].strip().split("/");

                EnumSet<WordTag> enumSet = EnumSet.noneOf(WordTag.class);
                for (String tag : tags) {
                    enumSet.add(WordTag.valueOf(tag));
                }

                glossary.put(key, enumSet);
            }
        }
    }

    public List<EnglishWord> sentenceToWord(String sentence) {
        Properties properties = new Properties();
        properties.put("annotators", "tokenize,ssplit,pos,lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
        CoreDocument document = pipeline.processToCoreDocument(sentence);

        // 词形还原, 去重, 提取标签和难度, 按照难度排序
        return document.tokens().stream()
                .map(CoreLabel::lemma).distinct()
                .map(this::mapToEnglishWord)
                .sorted().collect(Collectors.toList());
    }

    public List<EnglishWord> queryWordDifficulty(List<String> words){
        return words.stream().map(this::mapToEnglishWord).collect(Collectors.toList());
    }



    private EnglishWord mapToEnglishWord(String word) {
        if (glossary.containsKey(word)) {
            EnglishWord englishWord = new EnglishWord();
            englishWord.setWord(word);

            final EnumSet<WordTag> tags = glossary.get(word);
            int difficult = tags.stream().map(WordTag::getValue).reduce(0, Integer::sum);

            englishWord.setTag(tags.toString());
            englishWord.setDifficulty(difficult);

            return englishWord;
        } else {
            return new EnglishWord(word, "", 1);
        }
    }

}
