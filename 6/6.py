import sys
lines = list(map(str.strip, sys.stdin.readlines()))
nums = list(map(int, lines[0].split(",")))

counts = [0]*9
for x in nums:
    counts[x-1] += 1

for i in range(255):
    temp = counts[0]
    for j in range(len(counts)-1):
        counts[j] = counts[j+1]
    counts[6] += temp
    counts[8] = temp
    if i == 78:
        print("Day one:", sum(counts))
    if i == 254:
        print("Day two:", sum(counts))