<template>
  <div>
    <div class="form-floating my-3">
      <textarea class="form-control" id="textInputContent" placeholder="知识点正文"
                style="height: 280px" v-model="content" @keydown="hotKeyDispatcher"></textarea>
      <small id="textInputTitleHelp"
             class="form-text text-muted">输入需要添加到语料库中的句子</small>
      <label for="textInputContent">请输入句子</label>
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
  methods: {
    hotKeyDispatcher: function (e) {
      if ((e.ctrlKey || e.metaKey) && e.key === 'f') {
        e.preventDefault()
        console.log("Format Current Text.")
        this.formatSentence()
      }
    },
    formatSentence: function () {
      let c = this.content
      this.content = c.replace(/\n|\r/g, "").replace(/[.]/g, ".\n\n")
    }
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