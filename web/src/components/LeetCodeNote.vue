<template>
  <div>
    <div class="form-floating my-3">
      <input type="text" class="form-control" id="textInputTitle" aria-describedby="textInputTitleHelp"
             placeholder="知识点标题" v-model="title">
      <small id="textInputTitleHelp"
             class="form-text text-muted">简要的概括知识点的主要内容</small>
      <label for="textInputTitle">知识点标题</label>
    </div>

    <div class="form-floating mb-3">
            <textarea class="form-control" id="textInputContent" placeholder="知识点正文"
                      style="height: 100px" v-model="content"></textarea>
      <label for="textInputContent">知识点正文</label>
    </div>
  </div>
</template>

<script>
export default {
  name: "LeetCodeNote",
  data: function () {
    return {
      title: "",
      content: ""
    }
  }
}
</script>

<style scoped>

</style>