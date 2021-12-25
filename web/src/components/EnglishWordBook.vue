<template>
  <div>
    <div class="form-floating my-3">
      <input type="text" class="form-control" id="textInputTitle" aria-describedby="textInputTitleHelp"
             placeholder="请输入单词" v-model="title">
      <small id="textInputTitleHelp"
             class="form-text text-muted">简要的概括知识点的主要内容</small>
      <label for="textInputTitle">请输入单词</label>
    </div>

    <div class="form-floating mb-3">
            <textarea class="form-control" id="textInputContent" placeholder="Markdown代码"
                      style="height: 250px" v-model="content" @click="copyCode"></textarea>
      <label for="textInputContent">Markdown代码</label>
    </div>
  </div>
</template>

<script>
export default {
  name: "EnglishWordBook",
  props: {
    reset: Number,
  },
  data: function () {
    return {
      title: "",
      content: "",
    }
  },
  methods:{
    copyCode: function () {
      navigator.clipboard.writeText(this.content)
      console.log("Copied.")
    }
  },
  watch: {
    'title': function (newValue) {
      if (newValue === "") {
        this.content = ""
      }

      this.axios.get("/knowledge/generateWordMarkdown", {
        params: {
          "word": newValue
        }
      }).then(resp => this.content = resp.data)

    },
    'reset': function () {
      this.title = ""
      this.content = ""
    },
  }
}
</script>

<style scoped>

</style>