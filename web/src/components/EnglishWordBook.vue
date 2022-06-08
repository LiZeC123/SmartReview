<template>
  <div>
    <div class="form-floating my-3">
      <textarea class="form-control" id="textInputContent" placeholder="知识点正文"
                      style="height: 280px" v-model="content"></textarea>
      <small id="textInputTitleHelp"
             class="form-text text-muted">输入需要添加到语料库中的句子</small>
      <label for="textInputTitle">请输入句子</label>
    </div>




  </div>
</template>

<script>
export default {
  name: "EnglishWordBook",
  props: {
    submit: Number,
    reset: Number,
  },
  data: function () {
    return {
      content: "",
    }
  },
  methods:{

  },
  watch: {
    'reset': function () {
      this.content = ""
    },
    'submit': function () {
      this.$emit("on-submit", {"content": this.content})
    }
  }
}
</script>

<style scoped>

</style>