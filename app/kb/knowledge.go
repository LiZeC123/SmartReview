package kb

import (
	"fmt"
	"gorm.io/gorm"
	"time"
)

var db *gorm.DB

type Card struct {
	ID      uint   `json:"id"`
	Title   string `json:"title"`
	Content string `json:"content"`
}

type EnglishCorpusRecord struct {
	ID             uint `gorm:"primarykey"`
	Sentence       string
	Count          int8
	LastReviewTime time.Time
}

type ReviewRequest struct {
	ID          uint `json:"kid"`
	MemoryLevel uint `json:"memoryLevel"`
}

func Init(d *gorm.DB) {
	db = d
	err := db.AutoMigrate(&EnglishCorpusRecord{})
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
		err := db.Where("sentence = ?", sentence).First(&record).Error
		if err != nil {
			db.Create(&EnglishCorpusRecord{Sentence: sentence, Count: 0, LastReviewTime: time.Now()})
			fmt.Println("Insert Sentence:" + sentence)
		}
	}
}

func QueryRecentReview() []Card {
	var records []EnglishCorpusRecord

	// 从今天还没有复习过的数据中随机抽取10条数据返回
	yesterday := time.Now().AddDate(0, 0, -1)
	db.Where("LastReviewTime < ?", yesterday).Order(" RANDOM()").Limit(10).Find(&records)

	cards := make([]Card, len(records))
	for i, r := range records {
		cards[i] = Card{ID: r.ID, Title: "Corpus", Content: r.Sentence}
	}

	return cards
}

func FinishReview(r ReviewRequest) {
	var record EnglishCorpusRecord

	err := db.First(&record, r.ID).Error
	if err != nil {
		return
	}

	record.LastReviewTime = time.Now()
	record.Count += 1
	db.Save(&record)
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