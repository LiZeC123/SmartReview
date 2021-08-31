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
    return lemmatize_sentence(f['sentence'])




if __name__ == '__main__':
    app.run()
