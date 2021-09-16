<template>
  <div class="album py-5 bg-light h-100">
    <div class="container py-4" id="cardApp">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

        <div v-if="cards.length === 0" class="mx-auto">
          <img src="../../assets/finish.jpg" style="width: 100%" alt="庆祝一下吧!">
          <p class="text-center">太棒了, 现在没有要复习的知识点了</p>
        </div>

        <div class="col" v-for="(card, index) in cards" :key="card.id">
          <div class="card shadow-sm" @click="changeShowStatus(card)">
            <div class="card-header">{{ card.title }}</div>

            <div class="card-body" v-if="card.content !== '' && card.showContent">
              <h5 class="card-title">正文</h5>
              <p class="card-text">{{ card.content }}</p>
            </div>

            <div class="card-body" v-if="card.sentences !== []">
              <h5 class="card-title">相关句子</h5>
              <div class="my-2" v-for="sentence in card.sentences" :key="sentence.id">
                {{ sentence.content }}
              </div>
            </div>

            <div class="card-body" v-if="card.links.length !== 0">
              <h5 class="card-title">资源</h5>
              <ul>
                <li class="my-2" v-for="link in card.links" :key="link.url">
                  <a :href="link.url" target="_blank">{{ link.name }}</a>
                </li>
              </ul>
            </div>

            <div class="card-body">
              <span class="badge bg-secondary me-3" v-for="tag in card.tag" v-bind:key="tag.id">{{ tag.name }}</span>
            </div>

            <div class="card-footer" id="card-btn-group">
              <div class="d-flex justify-content-between align-items-center">

                <div>陌生</div>
                <div class="progress w-75" :id='"process-"+ card.id'
                     @mousedown="startProcess(card, $event)"
                     @mousemove="moveProcess(card, $event)"
                     @mouseup="stopProcess(card, index)">
                  <div class="progress-bar progress-bar-striped bg-success progress-bar-animated" role="progressbar"
                       :style="'width: ' + card.process + '%'"
                  ></div>
                </div>
                <div>熟悉</div>
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
  name: "ReviewRecentCard",
  data: function () {
    return {
      cards: []
    }
  },
  methods: {
    startProcess: function (card, event) {
      card.doProcess = true;
      card.beginX = event.offsetX;
      card.positionX = event.offsetX;
      card.beginClientX = event.clientX;
      card.processLength = document.getElementById("process-" + card.id).clientWidth;
      card.process = Math.round(card.positionX / card.processLength * 100)
    },
    moveProcess: function (card, event) {
      if (card.doProcess) {
        let moveX = event.clientX - card.beginClientX
        card.positionX = (card.beginX + moveX > card.processLength) ? card.processLength : (card.beginX + moveX < 0) ? 0 : card.beginX + moveX;
        card.process = Math.round(card.positionX / card.processLength * 100)
      }
    },
    stopProcess: function (card, idx) {
      card.doProcess = false;
      // TODO: 删除操作可以添加动画效果
      // https://cn.vuejs.org/v2/guide/transitions.html
      this.axios.post("/review/finishReview", {"kid": card.id, "memoryLevel": card.process})
          .then(() => this.cards.splice(idx, 1))
    },
    changeShowStatus: function (card) {
      card.showContent = !card.showContent;
    }
  },
  created() {
    this.axios({
      method: 'get',
      url: 'knowledge/queryRecentReview',
    }).then(response => {
      for (let card of response.data.data) {
        card.showContent = false
        card.process = 85
      }
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