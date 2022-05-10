package kb

import (
	"fmt"
	"github.com/LiZeC123/SmartReview/app/db"
	"time"
)

type Card struct {
	Title   string `json:"title"`
	Content string `json:"content"`
}

type EnglishCorpusRecord struct {
	ID             uint `gorm:"primarykey"`
	Sentence       string
	Count          int8
	LastReviewTime time.Time
}

func init() {
	err := db.Db.AutoMigrate(&EnglishCorpusRecord{})
	if err != nil {
		panic("EnglishWordRecord表自动迁移失败")
	}
}

func Migrate() {
	url := "https://lizec.top/note/%E5%8D%95%E8%AF%8D%E6%9C%AC.html"
	sentences, err := LoadWebRawFile(url)
	if err != nil {
		fmt.Println(err)
		return
	}

	for _, sentence := range sentences {
		var record EnglishCorpusRecord
		err := db.Db.Where("sentence = ?", sentence).First(&record).Error
		if err != nil {
			db.Db.Create(&EnglishCorpusRecord{Sentence: sentence, Count: 0, LastReviewTime: time.Now()})
			fmt.Println("Insert Sentence:" + sentence)
		}
	}
}

func QueryRecentReview() []Card {
	var records []EnglishCorpusRecord

	db.Db.Find(&records)

	cards := make([]Card, len(records))
	for idx, record := range records {
		cards[idx] = Card{Title: "Corpus", Content: record.Sentence}
	}

	return cards
}

//func GenerateWordMarkdown(c *gin.Context) {
//	word := c.DefaultQuery("word", "")
//	if word == "" {
//		c.String(http.StatusOK, "请指定需要插入的单词")
//	}
//
//	content := fmt.Sprintf("## %s\n\n\n", word)
//	content += fmt.Sprintf("> [Learner's Dictionary](https://www.learnersdictionary.com/definition/%s)", word)
//	content += fmt.Sprintf(" / [Merriam](https://www.merriam-webster.com/dictionary/%s)", word)
//	content += fmt.Sprintf(" / [Bing Image](https://cn.bing.com/images/search?q=%s)", word)
//	content += fmt.Sprintf(" / [Bing Dictionary](https://cn.bing.com/dict/search?q=%s)", word)
//
//	c.String(http.StatusOK, content)
//}
