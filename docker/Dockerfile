FROM node:14.17-alpine as web-build
WORKDIR /app

COPY web/package.json web/package-lock.json ./
RUN npm install

COPY web .
RUN npm run build

# =====================

FROM golang:1.17.5-alpine as go-build
WORKDIR /app

RUN apk update && \
    apk add --no-cache gcc musl-dev

COPY app/go.mod .
COPY app/go.sum .
RUN go mod download

COPY app .
RUN go build 

# =====================

FROM python:3.8-alpine
WORKDIR /app

RUN apk update && \
    apk add --no-cache tzdata && \
    apk add --no-cache nginx

COPY algo/requirements.txt /app/requirements.txt
RUN pip3 install -r requirements.txt

# 复制算法模块代码
COPY algo .

# 复制前端代码
COPY --from=web-build /app/dist /web
COPY docker/nginx.conf /etc/nginx/

# 复制后端可执行文件
COPY --from=go-build /app/app .

# 使用ENTRYPOINT限制容器只能执行下面的指令
CMD [ "sh", "-c", "nginx && ./app -d=true && python3 app.py" ]

# TODO： 可能需要安装Sqlite