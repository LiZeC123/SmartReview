import json
from typing import Dict

from flask import Flask, request

from tool4word import lemmatize_sentence, get_word_difficulty

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route("/sentenceToWord", methods=['POST'])
def sentence2word():
    f: Dict = request.get_json()
    return json.dumps(lemmatize_sentence(f['sentence']))


@app.route("/queryWordDifficulty", methods=['POST'])
def query_difficulty():
    return json.dumps(get_word_difficulty(request.get_json()))


if __name__ == '__main__':
    app.run("0.0.0.0", 5000, threaded=True)
