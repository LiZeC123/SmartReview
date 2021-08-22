<template>
  <div class="container">
    <div class="row py-5 my-3">
      <table class="table">
        <thead>
        <tr>
          <th>应用类型</th>
          <th>知识数量</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="record in records" :key="record.id">
          <th>{{ typeToName(record.name) }}</th>
          <th>{{ record.knowledgeCount }}</th>
          <th>
            <button class="btn btn-primary" @click="exportOne(record.id)">导出</button>
          </th>
        </tr>
        </tbody>
      </table>
    </div>

    <button class="btn btn-primary float-end" @click="exportAll()">导出全部数据</button>


    <!--    <nav aria-label="Page navigation example">-->
    <!--      <ul class="pagination  justify-content-center">-->
    <!--        <li class="page-item"><a class="page-link" href="#">上一页</a></li>-->
    <!--        <li class="page-item"><a class="page-link" href="#">1</a></li>-->
    <!--        <li class="page-item"><a class="page-link" href="#">2</a></li>-->
    <!--        <li class="page-item"><a class="page-link" href="#">3</a></li>-->
    <!--        <li class="page-item"><a class="page-link" href="#">下一页</a></li>-->
    <!--      </ul>-->
    <!--    </nav>-->
  </div>
</template>

<script>
export default {
  name: "ExportPage",
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
        default:
          return appType;
      }
    },
    exportOne: function (tagId) {
      this.$axios({
        method: "get",
        url: "export/exportOne",
        params: {"tagId": tagId}
      }).then(response => {
        if (response.data.success) {
          window.open(response.data.data);
        }
      })
    },
    exportAll: function () {
      this.$axios.post("export/exportAll", this.records.map(r => r.id))
          .then(response => {
            if (response.data.success) {
              window.open(response.data.data);
            }
          });
    }
  },
  created() {
    this.$axios({
      method: 'get',
      url: 'export/queryInfo',
    }).then(response => {
      this.records = response.data.data;
    })
  }
}
</script>

<style scoped>

</style>