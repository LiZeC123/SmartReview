<template>
  <div class="container">
    <div class="row py-5 my-3">
      <table class="table">
        <thead>
        <tr>
          <th>配额名称</th>
          <th>初始值</th>
          <th>消耗值</th>
          <th>当前配额</th>
          <th>下一次剩余时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(record, index) in records" :key="record.name">
          <th>{{ record.name }}</th>
          <th>{{ record.init }}</th>
          <th>{{ record.used }}</th>
          <th>{{ record.value }}</th>
          <th>{{ hourToDay(record.next) }}</th>
          <th>
            <button class="btn btn-secondary mx-2" @click="use(record.name)">消耗</button>
            <button class="btn btn-danger" @click="del(record.id, index)">删除</button>
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
  name: "ManagerQuotePage",
  data: function () {
    return {
      records: [
        {name: "默认值", init: 0, used: 0, value: 0.0, next: 0},
      ]
    }
  },
  mounted() {
    this.axios.post("")
  },
  methods: {
    use: function () {
    },
    del: function () {
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
  }
}
</script>

<style scoped>

</style>