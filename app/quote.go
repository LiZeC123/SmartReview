package main

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"math"
	"net/http"
	"time"
)

type Quote struct {
	gorm.Model
	UID      uint `gorm:"index"`
	Name     string
	Consumed float64
	Price    float64
	CD       float64 // 配额的最小冷却时间
}

type Task struct {
	gorm.Model
	Name  string
	Price float64
	CD    float64
	Count float64
}

func init() {
	err := db.AutoMigrate(&Quote{})
	if err != nil {
		panic("Quote表自动迁移失败")
	}

	err = db.AutoMigrate(&Task{})
	if err != nil {
		panic("Task表自动迁移失败")
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

	var quotes []Quote
	db.Find(&quotes)
	for _, q := range quotes {
		value -= q.Consumed * q.Price
	}

	var tasks []Task
	db.Find(&tasks)
	for _, t := range tasks {
		value += t.Price * t.Count
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

	db.Create(&Quote{UID: 1, Name: vo.Name, Price: vo.Price, CD: float64(vo.Cd)})
	c.JSON(http.StatusOK, gin.H{})
}

func QueryQuotes(c *gin.Context) {
	var quotes []Quote
	db.Find(&quotes)

	vo := make([]QuoteVO, len(quotes))
	now := time.Now().Unix()

	for i, q := range quotes {
		cd := q.CD - (float64(now-q.UpdatedAt.Unix()) / 3600)
		if cd < 0 {
			cd = 0
		}

		vo[i] = QuoteVO{Id: q.ID, Name: q.Name, Price: q.Price, Cd: int16(math.Ceil(cd))}
	}

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
	if err := db.First(&quote, quoteId.Id).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	quote.Consumed += 1
	db.Save(&quote)
	c.JSON(http.StatusOK, gin.H{})
}

func DeleteQuote(c *gin.Context) {
	var quoteId UID
	if err := c.ShouldBindJSON(&quoteId); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	db.Delete(&Quote{}, quoteId.Id)
	c.JSON(http.StatusOK, gin.H{})
}

type TaskVO struct {
	Id    uint    `json:"id"`
	Name  string  `json:"name"`
	Price float64 `json:"price"`
	Cd    int16   `json:"cd"`
}

func QueryTasks(c *gin.Context) {
	var tasks []Task
	db.Find(&tasks)

	vo := make([]TaskVO, len(tasks))
	now := time.Now().Unix()

	for i, t := range tasks {
		cd := t.CD - (float64(now-t.UpdatedAt.Unix()) / 3600)
		if cd < 0 {
			cd = 0
		}

		vo[i] = TaskVO{Id: t.ID, Name: t.Name, Price: t.Price, Cd: int16(math.Ceil(cd))}
	}

	c.JSON(http.StatusOK, vo)
}

func CreateTask(c *gin.Context) {
	var vo TaskVO
	if err := c.ShouldBindJSON(&vo); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := db.Create(&Task{Name: vo.Name, Price: vo.Price, CD: float64(vo.Cd)}).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{})
}

func FinishTask(c *gin.Context) {
	var taskId UID
	if err := c.ShouldBindJSON(&taskId); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	var task Task
	if err := db.First(&task, taskId.Id).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	task.Count += 1
	db.Save(&task)
	c.JSON(http.StatusOK, gin.H{})
}

func DeleteTask(c *gin.Context) {
	var taskId UID
	if err := c.ShouldBindJSON(&taskId); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	db.Delete(&Task{}, taskId.Id)
	c.JSON(http.StatusOK, gin.H{})
}

//func GetQuoteByUser(UID uint) {
//
//	var quotes []Quote
//
//	db.Where("UID = ?", UID).Find(&quotes)
//}
