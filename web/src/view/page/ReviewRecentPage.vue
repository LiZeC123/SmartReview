<template>
  <card-group :init-cards="initCard" :finish-card="finishCard" :show-process="true"></card-group>
</template>

<script>
import CardGroup from "../../components/CardGroup";
export default {
  name: "ReviewRecentCard",
  components: {CardGroup},
  methods: {
    initCard: function () {
      return this.axios.get('knowledge/queryRecentReview')
    },
    finishCard: function (kid, process) {
      return this.axios.post("knowledge/finishReview", {"kid": kid, "memoryLevel": process})
    },
  },
}
</script>

<style scoped>

</style>