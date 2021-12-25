package main

import (
	"fmt"
	"github.com/LiZeC123/SmartReview/app/web"
	"github.com/gin-gonic/gin"
	"net/http"
)

type Card struct {
	Title   string `json:"title"`
	Content string `json:"content"`
}

func QueryRecentReview(c *gin.Context) {
	url := "https://lizec.top/note/%E5%8D%95%E8%AF%8D%E6%9C%AC.html"
	words, err := web.LoadEnglishWordList(url)
	if err != nil {
		fmt.Println(err)
		return
	}

	cards := make([]Card, len(words))

	for idx, word := range words {
		cards[idx] = Card{Title: word, Content: ""}
	}

	c.JSON(http.StatusOK, cards)
}

func GenerateWordMarkdown(c *gin.Context) {
	word := c.DefaultQuery("word", "")
	if word == "" {
		c.String(http.StatusOK, "请指定需要插入的单词")
	}

	content := fmt.Sprintf("## %s\n\n\n", word)
	content += fmt.Sprintf("> [Learner's Dictionary](https://www.learnersdictionary.com/definition/%s)", word)
	content += fmt.Sprintf("  [Merriam](https://www.merriam-webster.com/dictionary/%s)", word)
	content += fmt.Sprintf("  [Bing Image](https://cn.bing.com/images/search?q=%s)", word)
	content += fmt.Sprintf("  [Bing Dictionary](https://cn.bing.com/dict/search?q=%s)", word)

	c.String(http.StatusOK, content)
}
