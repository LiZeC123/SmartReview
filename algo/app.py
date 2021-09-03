import json
from typing import Dict

from flask import Flask, request

from tool4word import lemmatize_sentence

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route("/sentenceToWord", methods=['POST'])
def sentence2word():
    f: Dict = request.get_json()
    ans = json.dumps(lemmatize_sentence(f['sentence']))
    print(ans)
    return ans




if __name__ == '__main__':
    app.run("0.0.0.0",5000, threading=True)
