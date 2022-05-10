package kb

import (
	"github.com/PuerkitoBio/goquery"
	"net/http"
	"strings"
)

func LoadWebRawFile(url string) ([]string, error) {
	resp, err := http.Get(url)

	if err != nil {
		return nil, err
	}

	doc, err := goquery.NewDocumentFromReader(resp.Body)
	if err != nil {
		return nil, err
	}

	content := doc.Find(".article-entry").Find("p")
	// 最后三个元素时博客的尾部版权说明, 直接省略
	length := len(content.Nodes) - 3

	ans := make([]string, 0, length)
	for i := 0; i < length; i++ {
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
