package main

import (
	"github.com/LiZeC123/SmartReview/app/db"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"math"
	"net/http"
	"sort"
	"time"
)

type Quote struct {
	gorm.Model
	UID   uint `gorm:"index"`
	Name  string
	Price float64
	CD    float64 // 配额的最小冷却时间
}

type QuoteRecord struct {
	gorm.Model
	Name  string
	Value float64
}

func init() {
	err := db.Db.AutoMigrate(&Quote{})
	if err != nil {
		panic("Quote表自动迁移失败")
	}

	err = db.Db.AutoMigrate(&QuoteRecord{})
	if err != nil {
		panic("QuoteRecord表自动迁移失败")
	}
}

type Count struct {
	Name  string  `json:"name"`
	Value float64 `json:"value"`
	Speed float64 `json:"speed"`
}

func QueryCounts(c *gin.Context) {
	const DaySecond = 24 * 60 * 60
	start := time.Date(2022, 1, 1, 0, 0, 0, 0, time.Local)
	now := time.Now().Unix()
	value := float64(now-start.Unix()) / DaySecond
	speed := 1.0 / 24

	var records []QuoteRecord
	db.Db.Find(&records)
	for _, q := range records {
		value += q.Value
	}

	counts := make([]Count, 1)
	counts[0] = Count{Name: "栗子币", Value: value, Speed: speed}

	c.JSON(http.StatusOK, counts)
}

type QuoteVO struct {
	Id    uint    `json:"id"`
	Name  string  `json:"name"`
	Price float64 `json:"price"`
	Cd    int16   `json:"cd"`
}

func CreateQuote(c *gin.Context) {
	var vo QuoteVO
	if err := c.ShouldBindJSON(&vo); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	db.Db.Create(&Quote{UID: 1, Name: vo.Name, Price: vo.Price, CD: float64(vo.Cd)})
	c.JSON(http.StatusOK, gin.H{})
}

func QueryQuotes(c *gin.Context) {
	var quotes []Quote
	db.Db.Find(&quotes)

	vo := make([]*QuoteVO, len(quotes))
	now := time.Now().Unix()

	for i, q := range quotes {
		cd := q.CD - (float64(now-q.UpdatedAt.Unix()) / 3600)
		if cd < 0 {
			cd = 0
		}

		vo[i] = &QuoteVO{Id: q.ID, Name: q.Name, Price: q.Price, Cd: int16(math.Ceil(cd))}
	}

	sort.Slice(vo, func(i, j int) bool {
		return vo[i].Cd < vo[j].Cd
	})

	c.JSON(http.StatusOK, vo)
}

type UID struct {
	Id uint `json:"id"`
}

func ConsumeQuote(c *gin.Context) {
	var quoteId UID
	if err := c.ShouldBindJSON(&quoteId); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	var quote Quote
	if err := db.Db.First(&quote, quoteId.Id).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	db.Db.Save(&quote)

	record := QuoteRecord{Name: quote.Name, Value: -quote.Price}
	db.Db.Save(&record)

	c.JSON(http.StatusOK, gin.H{})
}

func DeleteQuote(c *gin.Context) {
	var quoteId UID
	if err := c.ShouldBindJSON(&quoteId); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	db.Db.Delete(&Quote{}, quoteId.Id)
	c.JSON(http.StatusOK, gin.H{})
}

//func GetQuoteByUser(UID uint) {
//
//	var quotes []Quote
//
//	db.Where("UID = ?", UID).Find(&quotes)
//}
