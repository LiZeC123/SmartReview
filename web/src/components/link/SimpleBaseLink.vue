<template>
  <link-submit-base :builtin-links="[]" :init-commit-links="commitLinks" :clear="clear" :submit="submit"
                    @link-change="commit"></link-submit-base>
</template>

<script>
import LinkSubmitBase from "@/components/link/LinkSubmitBase";


export default {
  components: {LinkSubmitBase},
  name: "SimpleBaseLink",
  props: {
    title: String,
    links: Array,
    submit: Boolean,
    clear:Boolean
  },
  computed: {
    commitLinks: function () {
      if (this.links === undefined) {
        return [];
      } else {
        return this.links;
      }
    },
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