package kb

import (
	"bufio"
	"fmt"
	"sort"
	"strings"
	"time"

	"gorm.io/gorm"
)

var db *gorm.DB

type Card struct {
	ID      uint   `json:"id"`
	Title   string `json:"title"`
	Count   uint8  `json:"count"`
	Content string `json:"content"`
}

type EnglishCorpusRecord struct {
	ID             uint `gorm:"primarykey"`
	Sentence       string
	Count          uint8
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

func CreateEnglishCorpus(sentence string) {
	sc := bufio.NewScanner(strings.NewReader(sentence))

	for sc.Scan() {
		text := strings.TrimSpace(sc.Text())
		if text != "" {
			record := EnglishCorpusRecord{Sentence: text, Count: 0, LastReviewTime: time.Now()}
			db.Create(&record)
			fmt.Println("Insert Sentence:" + text)
		}
	}
}

func CreateDefaultKnowledge() {
	fmt.Println("Error: Default Knowledge Creater Called.")
}

func Export() []byte {
	return []byte("Hello World~")
	//return make([]byte, 0)
}

func QueryRecentReview() []Card {
	var records []EnglishCorpusRecord

	// 如果数据量足够大, 则先随机抽取一部分句子
	db.Order(" RANDOM()").Limit(45).Find(&records)

	// 然后根据某种算法返回最需要复习的15个句子, 此处按照复习次数排序
	// 于页面上分为三列显示，因此获取数据量选择3的倍数能够让界面看起来相对更整齐
	sort.Slice(records, func(i, j int) bool {
		return records[i].Count < records[j].Count
	})

	return toCard(records[:15])
}

func QueryWordCorpus(word string) []Card {
	var records []EnglishCorpusRecord
	word = strings.TrimSpace(word)
	if word != "" {
		db.Where("sentence LIKE ?", "%"+word+"%").Limit(45).Find(&records)
	}

	return toCard(records)
}

func toCard(records []EnglishCorpusRecord) []Card {
	cards := make([]Card, len(records))
	for i, r := range records {
		title := "知识点"
		cards[i] = Card{ID: r.ID, Title: title, Count: r.Count, Content: r.Sentence}
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
