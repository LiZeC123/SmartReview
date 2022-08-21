<template>
  <div class="album py-5">
    <div class="container py-4" id="cardApp">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

        <div v-if="cards.length === 0" class="mx-auto">
          <img src="../assets/finish.jpg" style="width: 100%" alt="庆祝一下吧!">
          <p class="text-center">太棒了, 现在没有要复习的知识点了</p>
        </div>

        <vue-context-menu :contextMenuData="contextMenuData" @openSD="openSD" @openMD="openMD"
                          @openBI="openBI" @openBD="openBD" @openWords="openWords"></vue-context-menu>

        <div class="col" v-for="(card, index) in cards" :key="card.id">
          <div class="card shadow-sm" @click="changeShowStatus(card)" @contextmenu="contextmenu">
            <div class="card-header"><span>{{ card.title }}</span> <span style="float:right">已复习{{ card.count }}次</span>
            </div>
            <div class="card-body" v-if="card.content !== ''">
              <p class="card-text">{{ card.content }}</p>
            </div>

            <div class="card-footer" v-if="showProcess">
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
  name: "CardGroup",
  props: {
    initCards: Function,
    finishCard: Function,
    showProcess: Boolean
  },
  data: function () {
    return {
      cards: [{title: "Title", content: "Empty Content", count: 0}],
      word: "",
      contextMenuData: {
        // the contextmenu name(@1.4.1 updated)
        menuName: "demo",
        // The coordinates of the display(菜单显示的位置)
        axis: {
          x: null,
          y: null
        },
        // Menu options (菜单选项)
        menulists: [
          {
            fnHandler: "openSD", // 绑定事件
            btnName: "初阶词典" // 菜单名称
          },
          {
            fnHandler: "openMD",
            btnName: "韦氏词典"
          },
          {
            fnHandler: "openBI",
            btnName: "必应图片"
          },
          {
            fnHandler: "openBD",
            btnName: "必应词典"
          },
          {
            fnHandler: "openWords",
            btnName: "相关句子"
          }
        ]
      }
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
      this.finishCard(card.id, card.process).then(() => this.cards.splice(idx, 1))
    },
    changeShowStatus: function (card) {
      card.showContent = !card.showContent;
    },
    openSD() {
      open("https://www.learnersdictionary.com/definition/" + this.word)
    },
    openMD() {
      open("https://www.merriam-webster.com/dictionary/" + this.word)
    },
    openBI() {
      open("https://cn.bing.com/images/search?q=" + this.word)
    },
    openBD() {
      open("https://cn.bing.com/dict/search?q=" + this.word)
    },
    openWords() {
      open("/home/related?word=" + this.word)
    },
    contextmenu(e) {
      e.preventDefault();
      const x = e.clientX;
      const y = e.clientY;
      // 获得当前鼠标的位置
      this.contextMenuData.axis = {x, y};
      // 获得当前选择的单词
      this.word = window.getSelection().toString();
    }
  },
  mounted() {
    this.initCards().then(response => {
      for (let card of response.data) {
        card.showContent = false
        card.process = 50
      }
      this.cards = response.data;
    })
  }
}
</script>

<style scoped>

</style>