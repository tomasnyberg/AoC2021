import sys
lines = list(map(str.strip, sys.stdin.readlines()))

matrix = [[0 for _ in range(2000)] for _ in range(2000)]

def check():
    result = 0
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            if matrix[i][j] >= 2:
                result += 1
    return result

for line in lines:
    a, b = line.split(" -> ")
    x1, y1, = map(int,a.split(","))
    x2, y2, = map(int,b.split(","))
    if x1 == x2 or y1 == y2:
        for i in range(min(x1, x2), max(x1, x2)+1):
            for j in range(min(y1, y2), max(y1, y2)+1):
                matrix[j][i] += 1
    else:
        coords = [[x1, y1], [x2,y2]]
        coords.sort()
        if coords[0][1] <= coords[1][1]:
            while coords[0][0] <= coords[1][0]:
                matrix[coords[0][1]][coords[0][0]] += 1
                coords[0][0] += 1
                coords[0][1] += 1
        else:
            while coords[0][0] <= coords[1][0]:
                matrix[coords[0][1]][coords[0][0]] += 1
                coords[0][0] += 1
                coords[0][1] -= 1
print(check())
