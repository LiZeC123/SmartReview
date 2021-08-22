<template>
  <div>
    <knowledge-form :do-submit="doSubmit" :show-message="showMessage" :do-show="doShow"
                    @submit="createKnowledge" @after-show="afterShow"></knowledge-form>
    <button type="submit" class="btn btn-primary float-end me-4" @click="sendSubmit">创建知识点</button>
  </div>
</template>

<script>
import KnowledgeForm from "@/components/KnowledgeForm";

export default {
  name: "CreateKnowledgePage",
  components: {KnowledgeForm},
  data: function () {
    return {
      doSubmit: false,
      doShow: false,
      showMessage: "知识点已进入自动复习队列"
    }
  },
  methods: {
    createKnowledge: function (knowledge) {
      console.log(knowledge);
      this.$axios.post('/knowledge/create', knowledge).then(response => {
        if (response.data.success) {
          this.doSubmit = false;
          this.doShow = true;
        }
      })
    },
    sendSubmit: function () {
      this.doSubmit = true;
    },
    afterShow: function () {
      this.doShow = false;
    }
  }

}
</script>

<style scoped>

</style>