<template>
  <div class="form-floating mb-3">
    <h6>勾选此知识点的标签</h6>
    <div class="form-check form-check-inline" v-for="tag in tagList" :key="tag.id">
      <input class="form-check-input" type="checkbox" id="inlineCheckbox1" :value="tag.name" v-model="tags">
      <label class="form-check-label" for="inlineCheckbox1">{{ tag.name }}</label>
    </div>
  </div>
</template>

<script>
export default {
  name: "KnowledgeTag",
  props:{
    reset: Number,
    submit: Number
  },
  data: function () {
    return {
      tags: [],
      tagList: [],
    }
  },
  created() {
    this.axios.get("/tag/selectAll").then(response => this.tagList = response.data.data)
  },
  watch: {
    'reset': function () {
      this.tags = []
    },
    'submit': function () {
      this.$emit("on-submit", this.tags)
    }
  }
}
</script>

<style scoped>

</style>