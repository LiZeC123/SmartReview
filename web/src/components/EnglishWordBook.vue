<template>
  <div>
    <div class="form-floating mb-3">
        <textarea class="form-control" id="textInputContent" placeholder="请输入英文句子"
                  style="height: 100px" v-model="content"></textarea>
      <label for="textInputContent">请输入英文句子</label>
    </div>

    <div>
      <h6>勾选需要复习的单词</h6>
      <div class="form-check form-check-inline" v-for="w in wordList" :key="w.word">
        <input class="form-check-input" type="checkbox" id="inlineCheckbox1" :value="w.word" v-model="words">
        <label class="form-check-label" for="inlineCheckbox1">{{ w.word }}({{ w.tag }}) </label>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "EnglishWordBook",
  data: function () {
    return {
      content: "",
      wordList: [],
      words: [],
    }
  },
  watch: {
    'content': function (newValue) {
      this.$axios({
        method:"POST",
        url: "/sentence/toWord",
        params: {
          "sentence": newValue
        }
      }).then(resp => this.wordList = resp.data.data);
    }

  }
}
</script>

<style scoped>

</style>