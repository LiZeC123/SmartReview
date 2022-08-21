<template>
  <div class="container">
    <div class="row py-5 my-3">
      <table class="table">
        <thead>
        <tr>
          <th>知识点ID</th>
          <th>内容</th>
          <th>复习次数</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(record, index) in records" :key="record.id">
          <th>{{ record.id }}</th>
          <th style="max-width: 500px">{{ record.content }}</th>
          <th>{{ record.count }}</th>
          <th>
            <button class="btn btn-secondary mx-2" @click="modifyKnowledge(record.id)">详情</button>
            <button class="btn btn-danger" @click="deleteKnowledge(record.id, index)">删除</button>
          </th>
        </tr>
        </tbody>
      </table>
    </div>


    <nav aria-label="Page navigation">
      <ul class="pagination  justify-content-center">
        <li class="page-item"><a class="page-link" href="#" @click="firstPage">首页</a></li>
        <li class="page-item"><a class="page-link" href="#" @click="prePage">上一页</a></li>
        <li class="page-item"><a class="page-link" href="#" @click="nextPage">下一页</a></li>
        <li class="page-item"><a class="page-link" href="#" @click="lastPage">末页</a></li>
      </ul>
    </nav>
  </div>
</template>


<script>
export default {
  name: "ManageKnowledgePage",
  data: function () {
    return {
      records: [
        {id: 0, title: "Title", content: "Content", count: 0},
      ],
      pageSize: 10,
      currentPage: 0,
      totalNum: 0,
    }
  },
  computed: {
    maxPage: function () {
      return Math.ceil(this.totalNum / this.pageSize)
    }
  },
  methods: {
    typeToName: function (appType) {
      switch (appType) {
        case "1":
          return "英语单词本"
        case "2":
          return "力扣题解";
        default:
          return "基础记录";
      }
    },
    hourToDay: function (currentInterval) {
      if (currentInterval < 24) {
        return currentInterval + '小时';
      }

      let day = Math.floor(currentInterval / 24) + '天';
      if (currentInterval % 24 !== 0) {
        day += (currentInterval % 24) + '小时';
      }

      return day;
    },
    instantToTime: function (nextReviewTime) {
      let date = new Date(nextReviewTime);
      return date.toLocaleString("en-GB")
    },
    initKnowledgeList: function () {
      this.axios.post("knowledge/queryKnowledgeCount").then(res => {
        console.log(res.data)
        this.totalNum = res.data
      })
      this.firstPage()
    },
    deleteKnowledge: function (id, index) {
      this.axios.post("knowledge/deleteKnowledge", {"id": id})
          .then(() => this.records.splice(index, 1))
    },
    firstPage: function () {
      this.currentPage = 0
      this.getPage()
    },
    nextPage: function () {
      if (this.currentPage === this.maxPage) {
        return;
      }

      this.currentPage++
      this.getPage()
    },
    prePage: function () {
      if (this.currentPage === 0) {
        return;
      }
      this.currentPage--
      this.getPage()
    },
    lastPage: function () {
      this.currentPage = this.maxPage;
      this.getPage()
    },
    getPage: function () {
      const data = {
        "pageIndex": this.currentPage,
        "pageSize": this.pageSize
      }
      this.axios.post("knowledge/queryKnowledgePage", data).then(res => {
        this.records = res.data
      })
    }

  },
  created() {
    this.initKnowledgeList();
  },
}
</script>

<style scoped>

</style>