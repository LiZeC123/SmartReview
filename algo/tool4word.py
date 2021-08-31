from typing import List

from nltk import word_tokenize, pos_tag
from nltk.corpus import wordnet
from nltk.stem import WordNetLemmatizer

# !python -m nltk.downloader wordnet punkt averaged_perceptron_tagger

# 不同类别的单词对应的分数
level_value = {"HS": -5, "COCA": 0, "CET": 1, "GRE": 2, "TOEFL": 2}


def load_level_dictionary():
    dictionary = {}
    with open("data/Glossary/dataset.cvs") as f:
        for line in f:
            word, tag = line.split(",")
            word = word.strip()
            tag = tag.strip()

            tags = tag.split("/")
            value = 0
            for t in tags:
                value += level_value[t]
            dictionary[word] = (tag, value)
    return dictionary


level_dict = load_level_dictionary()
wnl = WordNetLemmatizer()


# 获取单词的词性
def get_wordnet_pos(tag):
    if tag.startswith('J'):
        return wordnet.ADJ
    elif tag.startswith('V'):
        return wordnet.VERB
    elif tag.startswith('N'):
        return wordnet.NOUN
    elif tag.startswith('R'):
        return wordnet.ADV
    else:
        return wordnet.NOUN


def lemmatize_sentence(sentence: str) -> List:
    tokens = word_tokenize(sentence.lower())
    tagged = pos_tag(tokens)

    ans = []
    for word, tag in tagged:
        origin_word = wnl.lemmatize(word, get_wordnet_pos(tag))
        if len(origin_word) == 1:
            continue
        if origin_word in level_dict:
            level_tag = level_dict[origin_word]
        else:
            level_tag = ('', 0)
        ans.append((origin_word, level_tag))
    # Example: [
    #   ('comprehensive', (' GRE/COCA/CET/TOEFL', 5)),
    #   ('feature', (' GRE/COCA/CET', 3))
    # ]
    return ans
