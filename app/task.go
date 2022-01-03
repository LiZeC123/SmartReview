package main

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"math"
	"net/http"
	"sort"
	"time"
)

type Task struct {
	gorm.Model
	Name   string
	Weight float64
	CD     float64
}

var WeightSum = 0.0

func updateWeightSum() {
	var tasks []Task
	db.Find(&tasks)
	weightSum := 0.0
	for _, t := range tasks {
		weightSum += t.Weight
	}

	WeightSum = weightSum
}

func init() {
	err := db.AutoMigrate(&Task{})
	if err != nil {
		panic("Task表自动迁移失败")
	}

	updateWeightSum()
}

type TaskVO struct {
	Id    uint    `json:"id"`
	Name  string  `json:"name"`
	Price float64 `json:"price"`
	Cd    int16   `json:"cd"`
}

const TaskTotalValue = (1.0 / 24) * 28 / 3

func QueryTasks(c *gin.Context) {
	now := time.Now().Unix()

	var tasks []Task
	db.Find(&tasks)

	vo := make([]*TaskVO, len(tasks))
	for i, t := range tasks {
		cd := t.CD - (float64(now-t.UpdatedAt.Unix()) / 3600)
		if cd < 0 {
			cd = 0
		}

		vo[i] = &TaskVO{Id: t.ID, Name: t.Name, Price: calcPrice(t.Weight, t.CD), Cd: int16(math.Ceil(cd))}
	}

	sort.Slice(vo, func(i, j int) bool {
		return vo[i].Cd < vo[j].Cd
	})

	c.JSON(http.StatusOK, vo)
}

func calcPrice(weight, cd float64) float64 {
	base := weight / WeightSum * TaskTotalValue
	if cd < 12 {
		base = base / 2
	}
	return base
}

func CreateTask(c *gin.Context) {
	var vo TaskVO
	if err := c.ShouldBindJSON(&vo); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := db.Create(&Task{Name: vo.Name, Weight: vo.Price, CD: float64(vo.Cd)}).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	updateWeightSum()

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
	db.Save(&task)

	record := QuoteRecord{Name: task.Name, Value: calcPrice(task.Weight, task.CD)}
	db.Save(&record)

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
