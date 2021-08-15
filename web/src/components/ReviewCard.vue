<template>
  <div class="album py-5 bg-light">
    <div class="container py-4" id="cardApp">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

        <div v-if="cards.length === 0" class="mx-auto">
          <img src="../assets/finish.jpg" style="width: 100%" alt="庆祝一下吧!">
          <p class="text-center">太棒了, 现在没有要复习的知识点了</p>
        </div>

        <div class="col" v-for="(card, index) in cards" :key="card.id">
          <div class="card shadow-sm">
            <div class="card-header">{{ card.title }}</div>

            <div class="card-body" v-if="card.content !== ''">
              <p class="card-text">{{ card.content }}</p>
            </div>

            <div class="card-body">
              <h5 class="card-title">资源</h5>
              <ul>
                <li class="my-2" v-for="link in card.link" :key="link.url"><a :href="link.url"
                                                                              target="_blank">{{ link.name }}</a>
                </li>
              </ul>
            </div>

            <div class="card-body">
              <span class="badge bg-secondary me-3" v-for="tag in card.tag" v-bind:key="tag[0]">{{ tag }}</span>
            </div>

            <div class="card-footer" id="card-btn-group">
              <div class="d-flex justify-content-between align-items-center">
                <button @click="finishCard(card.id, index, 3)"
                        class="btn btn-outline-success" type="button">熟悉
                </button>
                <button @click="finishCard(card.id, index, 2)"
                        class="btn btn-outline-secondary" type="button">记得
                </button>
                <button @click="finishCard(card.id, index, 1)"
                        class="btn btn-outline-secondary" type="button">陌生
                </button>
                <button @click="finishCard(card.id, index, 0)"
                        class="btn btn-outline-danger" type="button">遗忘
                </button>
              </div>
            </div>

          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ReviewCard",
  data: function () {
    return {
      cards: []
    }
  },
  methods: {
    // TODO: 删除操作可以添加动画效果
    // https://cn.vuejs.org/v2/guide/transitions.html
    finishCard: function (id, idx, memoryLevel) {
      this.$axios({
        method: 'get',
        url: "/knowledge/finishReview",
        params: {"kid": id, "memoryLevel": memoryLevel},
      }).then(response => {
        if (response.data.success) {
          this.cards.splice(idx, 1);
        }
      });
    }
  },
  created() {
    this.$axios({
      method: 'get',
      url: 'knowledge/queryRecentReview',
    }).then(response => {
      this.cards = response.data.data;
    })
  }
}
</script>

<style scoped>

#card-btn-group > div > button {
  font-size: 14px;
}

</style>