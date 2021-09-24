<template>
  <div>


  </div>
</template>

<script>

export default {
  name: "ModifyKnowledgePage",
  components: {},
  data: function () {
    return {
      doSubmit: false,
      doShow: false,
      showMessage: "知识点已内容已更新",
      knowledge: undefined
    }
  },
  methods: {
    updateKnowledge: function (knowledge) {
      console.log(knowledge);
      alert("更新功能当前不可用")
    },
    sendSubmit: function () {
      this.doSubmit = true;
    },
    afterShow: function () {
      this.doShow = false;
    }
  },
  created() {
    const id = this.$route.query.id;
    this.axios({
      method: "get",
      url: "/knowledge/queryDetail",
      params: {
        "kid": id
      }
    }).then(response => {
      console.log("getOne", response)
      if(response.data.success) {
        this.knowledge = response.data.data;
      }
    })
  }

}
</script>

<style scoped>

</style>