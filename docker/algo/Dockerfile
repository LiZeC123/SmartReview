  
FROM python:3.9.6-slim-buster

ENV TZ Asia/Shanghai

WORKDIR /app

# 必须安装了这个软件配置才会生效
RUN apk update && \
    apk add --no-cache tzdata

# 先执行依赖安装操作，后续如果依赖不变可以复用这一层
COPY algo/requirements.txt /app/requirements.txt

RUN pip3 install -r requirements.txt

# 复制项目代码到容器的app目录
COPY algo .

RUN python -m nltk.downloader wordnet punkt averaged_perceptron_tagger

EXPOSE 5000

# 使用ENTRYPOINT限制容器只能执行下面的指令
ENTRYPOINT [ "python3", "app.py"]