<template>
  <link-submit-base :builtin-links="updatedBuiltinLink"
                    @link-change="commit"> </link-submit-base>
</template>

<script>
import LinkSubmitBase from "@/components/link/LinkSubmitBase";

export default {
  components: {LinkSubmitBase},
  name: "EnglishWordBook",
  props:{
    title: String
  },
  computed: {
    updatedBuiltinLink: function () {
      if (this.title === "") {
        return []
      }

      return [
        {name: "初阶韦氏词典", url: "https://www.learnersdictionary.com/definition/" + this.title},
        {name: "高阶韦氏词典", url: "https://www.merriam-webster.com/dictionary/" + this.title},
        {name: "必应搜索图片", url: "https://cn.bing.com/images/search?q=" + this.title},
        {name: "必应搜索词典", url: "https://cn.bing.com/dict/search?q=" + this.title}
      ];
    }
  },
  methods: {
    commit: function (links) {
      this.$emit('link-change', links);
    }
  },
}
</script>

<style scoped>

</style>