package web

import (
	"github.com/PuerkitoBio/goquery"
	"net/http"
	"strings"
)

func LoadEnglishWordList(url string) ([]string, error) {

	resp, err := http.Get(url)

	if err != nil {
		return nil, err
	}

	doc, err := goquery.NewDocumentFromReader(resp.Body)
	if err != nil {
		return nil, err
	}

	content := doc.Find(".article-entry").Find("h2")

	ans := make([]string, 0, len(content.Nodes))
	for i := range content.Nodes {
		node := content.Eq(i)
		text := strings.TrimSpace(node.Text())
		ans = append(ans, text)
	}

	err = resp.Body.Close()
	if err != nil {
		return nil, err
	}

	return ans, nil
}
