<template>
  <div class="container">
    <div class="row py-5 my-3">
      <table class="table">
        <thead>
        <tr>
          <th>应用类型</th>
          <th>标题</th>
          <th>复习次数</th>
          <th>当前复习间隔</th>
          <th>下次复习时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(record, index) in records" :key="record.id">
          <th>{{ typeToName(record.appType) }}</th>
          <th>{{ record.title }}</th>
          <th>{{ record.reviewCount }}</th>
          <th>{{ hourToDay(record.intervalTime) }}</th>
          <th>{{ record.nextReviewTime }}</th>
          <th>
            <button class="btn btn-secondary mx-2" @click="modifyKnowledge(record.id)">详情</button>
            <button class="btn btn-danger" @click="deleteKnowledge(record.id, index)">删除</button>
          </th>
        </tr>
        </tbody>
      </table>
    </div>


    <nav aria-label="Page navigation example">
      <ul class="pagination  justify-content-center">
        <li class="page-item"><a class="page-link" href="#">上一页</a></li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">下一页</a></li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: "ManageKnowledgePage",
  data: function () {
    return {
      records: []
    }
  },
  methods: {
    typeToName: function (appType) {
      switch (appType) {
        case "EnglishWordBook":
          return "英语单词本"
        case "LeetCodeNote":
          return "力扣题解";
        case "Base":
          return "基础记录";
        default:
          return appType;
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
    initKnowledgeList: function () {
      this.$axios({
        method: 'get',
        url: 'review/queryAllRecord',
      }).then(response => {
        this.records = response.data.data;
      });
    },
    modifyKnowledge: function (id) {
      this.$router.push({
        name: "ModifyKnowledgePage",
        query: {
          "id": id
        }
      })
    },
    deleteKnowledge: function (id, index) {
      this.$axios({
        method: "get",
        url: "knowledge/deleteKnowledge",
        params: {
          "kid": id
        }
      }).then(response => {
        if (response.data.success) {
          this.records.splice(index, 1);
        }
      })
    }
  },
  created() {
    this.initKnowledgeList();
  }
}
</script>

<style scoped>

</style>