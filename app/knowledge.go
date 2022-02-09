package main

import (
	"fmt"
	"net/http"
	"time"

	"github.com/LiZeC123/SmartReview/app/web"
	"github.com/gin-gonic/gin"
)

type Card struct {
	Title   string `json:"title"`
	Content string `json:"content"`
}

type EnglishWordRecord struct {
	ID             uint   `gorm:"primarykey"`
	Word           string `gorm:"index"`
	Count          int8
	Level          int8
	Interval       int16
	NextReviewTime time.Time
}

func init() {
	err := db.AutoMigrate(&EnglishWordRecord{})
	if err != nil {
		panic("EnglishWordRecord表自动迁移失败")
	}
}

func Migrate(c *gin.Context) {
	migrateEnglishWordRecord()
	c.String(http.StatusOK, "Success")
}

func migrateEnglishWordRecord() {
	url := "https://lizec.top/note/%E5%8D%95%E8%AF%8D%E6%9C%AC.html"
	words, err := web.LoadEnglishWordList(url)
	if err != nil {
		fmt.Println(err)
		return
	}

	for _, word := range words {
		var record EnglishWordRecord
		err := db.Where("word = ?", word).First(&record).Error
		if err != nil {
			db.Create(&EnglishWordRecord{Word: word, Count: 0, Level: 0, Interval: 12, NextReviewTime: time.Now()})
			fmt.Println("Insert " + word)
		}
	}
}

func QueryRecentReview(c *gin.Context) {
	var records []EnglishWordRecord

	db.Find(&records)

	cards := make([]Card, len(records))
	for idx, record := range records {
		cards[idx] = Card{Title: record.Word, Content: ""}
	}

	c.JSON(http.StatusOK, cards)
}

func GenerateWordMarkdown(c *gin.Context) {
	word := c.DefaultQuery("word", "")
	if word == "" {
		c.String(http.StatusOK, "请指定需要插入的单词")
	}

	content := fmt.Sprintf("## %s\n\n\n", word)
	content += fmt.Sprintf(" > [Learner's Dictionary](https://www.learnersdictionary.com/definition/%s)", word)
	content += fmt.Sprintf(" / [Merriam](https://www.merriam-webster.com/dictionary/%s)", word)
	content += fmt.Sprintf(" / [Bing Image](https://cn.bing.com/images/search?q=%s)", word)
	content += fmt.Sprintf(" / [Bing Dictionary](https://cn.bing.com/dict/search?q=%s)", word)

	c.String(http.StatusOK, content)
}
