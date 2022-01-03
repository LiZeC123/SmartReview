<template>
  <div class="container">
    <div class="py-5 my-3">
      <h2>总体数据</h2>
      <table class="table">
        <thead>
        <tr>
          <th>名称</th>
          <th>当前值</th>
          <th>当前速率(每小时)</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(rec) in counts" :key="rec.id">
          <th>{{ rec.name }}</th>
          <th>{{ rec.value.toFixed(5) }}</th>
          <th>{{ rec.speed.toFixed(5) }}</th>
        </tr>
        </tbody>
      </table>
    </div>

    <div>
      <h2>配额列表</h2>
      <table class="table">
        <thead>
        <tr>
          <th>配额名称</th>
          <th>单位价格</th>
          <th>剩余冷却时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(record) in records" :key="record.id">
          <th>{{ record.name }}</th>
          <th>{{ record.price }}</th>
          <th>{{ hourToDay(record.cd) }}</th>
          <th>
            <button :class="['btn', 'me-2', 'my-1', quoteEnable(record) ? 'btn-success' : 'btn-secondary']"
                    :disabled="!quoteEnable(record)" @click="use(record.id)">消耗
            </button>
            <button class="btn btn-danger my-1" @click="delQuote(record.id)" disabled>删除</button>
          </th>
        </tr>
        </tbody>
      </table>

      <button class="btn btn-primary me-2 my-1" @click="createQuote">新建配额</button>
    </div>


    <div class="py-5 my-3">
      <h2>任务列表</h2>
      <table class="table">
        <thead>
        <tr>
          <th>任务名称</th>
          <th>任务价值</th>
          <th>剩余冷却时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(rec) in tasks" :key="rec.id">
          <th>{{ rec.name }}</th>
          <th>{{ rec.price.toFixed(3) }}</th>
          <th>{{ hourToDay(rec.cd) }}</th>
          <th>
            <button :class="['btn', 'me-2', 'my-1', taskEnable(rec) ? 'btn-success' : 'btn-secondary']"
                    :disabled="!taskEnable(rec)" @click="finishTask(rec.id)">完成任务
            </button>
            <button class="btn btn-danger my-1" @click="delTask(rec.id)" disabled>删除任务</button>
          </th>
        </tr>
        </tbody>
      </table>

      <button class="btn btn-primary me-2 my-1" @click="createTask">新建任务</button>
    </div>

  </div>
</template>

<script>
export default {
  name: "ManagerQuotePage",
  data: function () {
    return {
      counts: [
        {id: 0, name: "栗子", value: 0, speed: 233}
      ],
      records: [
        {id: 0, name: "栗子", price: 3, cd: 2},
      ],
      tasks: [
        {id: 0, name: "栗子", price: 2.3, cd: 23}
      ]
    }
  },
  mounted() {
    this.reload()
  },
  methods: {
    reload: function () {
      this.axios.get("/quote/queryCounts").then(res => this.counts = res.data)
      this.axios.get("/quote/queryQuotes").then(res => this.records = res.data)
      this.axios.get("/quote/queryTasks").then(res => this.tasks = res.data)
    },
    use: function (id) {
      this.axios.post("/quote/consumeQuote", {id}).then(() => this.reload())
    },
    createQuote: function () {
      let name = prompt("请输入配额名称")
      let price = parseFloat(prompt("请输入配额价值"))
      let cd = parseInt(prompt("请输入配额冷却时间"))

      this.axios.post("/quote/createQuote", {"id": 0, name, price, cd}).then(() => this.reload())
    },
    delQuote: function (id) {
      this.axios.post("/quote/deleteQuote", {id}).then(() => this.reload())
    },
    hourToDay: function (currentInterval) {
      if(currentInterval === 0) {
        return "冷却完毕"
      }

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
    quoteEnable: function (record) {
      const value = this.counts[0].value
      return record.price < value && (record.cd <= 0)
    },
    taskEnable: function (rec) {
      return rec.cd <= 0
    },
    finishTask: function (id) {
      this.axios.post("/quote/finishTask", {"id": id}).then(() => this.reload())
    },
    createTask: function () {
      let name = prompt("请输入任务名称")
      let price = parseFloat(prompt("请输入任务权重(1~3)"))
      let cd = parseInt(prompt("请输入任务冷却时间"))

      this.axios.post("/quote/createTask", {"id": 0, name, price, cd}).then(() => this.reload())
    },
    delTask: function (id) {
      this.axios.post("/quote/deleteTask", {id}).then(() => this.reload())
    }
  }
}
</script>

<style scoped>

</style>