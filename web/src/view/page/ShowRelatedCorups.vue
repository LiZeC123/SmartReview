<template>
  <card-group :init-cards="initCard"></card-group>
</template>

<script>
import CardGroup from "../../components/CardGroup";

export default {
  name: "ShowRelatedCorups",
  components: {CardGroup},
  methods: {
    initCard: function () {
      let word = this.$route.query.word
      return this.axios.post("knowledge/queryWordCorpus", {"word": word})
    },
  },
}
</script>

<style scoped>

</style>