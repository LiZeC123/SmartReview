from nltk import word_tokenize, pos_tag
from nltk.corpus import wordnet
from nltk.stem import WordNetLemmatizer
from typing import List

# !python -m nltk.downloader wordnet punkt averaged_perceptron_tagger

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


def lemmatize_sentence(sentence: str) -> List[str]:
    tokens = word_tokenize(sentence)
    tagged = pos_tag(tokens)

    wnl = WordNetLemmatizer()
    return list(map(lambda t: wnl.lemmatize(t[0], pos=get_wordnet_pos(t[1])), tagged))