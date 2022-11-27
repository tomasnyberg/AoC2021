import sys
lines = list(map(str.strip, sys.stdin.readlines()))

def solve(coords):
    nondiag = list(filter(lambda x: x[0] == x[2] or x[1] == x[3], coords))
    diag = list(filter(lambda x: not (x[0] == x[2] or x[1] == x[3]), coords))
    matrix = [[0 for _ in range(1002)] for _ in range(1002)]
    result = 0
    for x1, y1, x2, y2 in nondiag:
        if x1 == x2 or y1 == y2:
            for i in range(min(x1, x2), max(x1, x2)+1):
                for j in range(min(y1, y2), max(y1, y2)+1):
                    matrix[j][i] += 1 
                    if matrix[j][i] == 2:
                        result += 1
    print("Part one: ", result)
    for x1, y1, x2, y2 in diag:
        coords = [[x1, y1], [x2,y2]]
        coords.sort()
        inc = 1 if coords[0][1] <= coords[1][1] else -1
        while coords[0][0] <= coords[1][0]:
            matrix[coords[0][1]][coords[0][0]] += 1
            if matrix[coords[0][1]][coords[0][0]] == 2:
                result += 1
            coords[0][0] += 1
            coords[0][1] += inc
    print("Part two: ", result)

coords = []

for line in lines:
    a, b = line.split(" -> ")
    x1, y1, = map(int,a.split(","))
    x2, y2, = map(int,b.split(","))
    coords.append([x1,y1,x2,y2])

solve(coords)
