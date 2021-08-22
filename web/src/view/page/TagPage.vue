<template>
  <div class="container">
    <div class="row py-5 my-3">
      <table class="table">
        <thead>
        <tr>
          <th>标签名称</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="record in records" :key="record.id">
          <th>{{ record.name }}</th>
          <th>
            <button class="btn btn-danger" @click="deleteTag(record.id)">删除</button>
          </th>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="row">
      <div class="form-floating">
        <input type="text" class="form-control" id="textInputTitle" aria-describedby="textInputTitleHelp"
               placeholder="创建新标签" v-model="title" @keydown.enter="createTag">
        <small id="textInputTitleHelp"
               class="form-text text-muted">输入标签的名称并按回车键提交</small>
        <label for="textInputTitle">创建新标签</label>

      </div>
    </div>


  </div>
</template>

<script>
export default {
  name: "TagPage",
  data: function () {
    return {
      title: "",
      records: []
    }
  },
  methods: {
    createTag: function () {
      this.$axios({
        method: "get",
        url: 'tag/create',
        params: {"tagName": this.title}
      }).then(() => {
        this.title = "";
        this.fetchTag()
      });
    },
    deleteTag: function (tagId) {
      console.log("delete ", tagId);
      this.$axios({
        method: "get",
        url: 'tag/delete',
        params: {"tagId": tagId}
      }).then(() => this.fetchTag());
    },
    fetchTag: function () {
      this.$axios({
        method: 'get',
        url: 'tag/selectAll',
      }).then(response => {
        this.records = response.data.data;
      })
    }
  },
  created() {
    this.fetchTag();
  }
}
</script>

<style scoped>

</style>