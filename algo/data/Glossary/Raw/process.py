import shelve
from collections import defaultdict

files = ['CET.txt', 'COCA.txt', 'GRE.txt',"HS.txt", "TOEFL.txt"]

ans = defaultdict(set)
for file in files:
    tag = file.split(".")[0]
    with open(file) as f:
        for word in f:
            ans[word.strip()].add(tag)


with open("dataset.cvs", 'w') as f:
    for key, value in ans.items():
        f.write(f"{key}, {'/'.join(value)}\n")
