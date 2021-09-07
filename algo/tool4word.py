from typing import List

from nltk import word_tokenize, pos_tag
from nltk.corpus import wordnet
from nltk.stem import WordNetLemmatizer

# 不同类别的单词对应的分数
level_value = {"HS": 0, "COCA": 1, "CET": 2, "GRE": 4, "TOEFL": 3}


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
    # 单词转为小写后去重
    tokens = word_tokenize(sentence.lower())
    tagged = pos_tag(tokens)

    ans = {}
    for word, tag in tagged:
        origin_word = wnl.lemmatize(word, get_wordnet_pos(tag))
        if len(origin_word) == 1:
            continue
        if origin_word in level_dict:
            level_tag = level_dict[origin_word]
        else:
            level_tag = ('', 0)
        ans[origin_word] = level_tag

    ans = [(key, value) for key, value in ans.items()]

    # Example: [
    #   ('comprehensive', (' GRE/COCA/CET/TOEFL', 5)),
    #   ('feature', (' GRE/COCA/CET', 3))
    # ]
    return list(map(lambda x: {"word": x[0], "tag": x[1][0], "difficulty": x[1][1]},
                    sorted(ans, key=lambda x: x[1][1], reverse=True)))


def get_word_difficulty(words):
    ans = []
    for word in words:
        if word in level_dict:
            ans.append({
                "word": word,
                "difficulty": level_dict[word][1]
            })
        else:
            ans.append({
                "word": word,
                "difficulty": 0
            })
    return ans


if __name__ == '__main__':
    v = lemmatize_sentence("Hello World")
    print(v)
