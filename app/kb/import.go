package kb

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"time"
)

func ImportEnglishRecord(file os.File) error {
	reader := bufio.NewReader(&file)

	for {
		sentence, err := reader.ReadString('\n')
		if err == io.EOF {
			return nil
		}

		if err != nil {
			return fmt.Errorf("error: %v where import file %v", err, file)
		}

		var record EnglishCorpusRecord
		err = db.Where("sentence = ?", sentence).First(&record).Error
		if err != nil {
			db.Create(&EnglishCorpusRecord{Sentence: sentence, Count: 0, LastReviewTime: time.Now()})
			fmt.Println("Insert Sentence:" + sentence)
		}
	}
}
