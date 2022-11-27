import sys
lines = list(map(str.strip, sys.stdin.readlines()))

bingonums = list(map(int, lines[0].split(",")))

boards = []
for i in range(2, len(lines), 6):
    matrix = []
    for j in range(i, i+5):
        lines[j] = lines[j].replace("  ", " ")
        matrix.append(list(map(int, lines[j].split(" "))))
    boards.append(matrix)

def check_bingo(board):
    for row in range(len(board)):
        if all(x < 0 for x in board[row]):
            return True
    for col in range(len(board[0])):
        good = True
        for row in range(len(board)):
            if board[row][col] >= 0:
                good = False
        if good: return True
    return good

def check_score(board, last):
    result = 0 
    for xs in board:
        for x in xs:
            if x >= 0:
                result += x
    return result * last

broken = False


won = set()
for x in bingonums:
    if broken: break
    for k in range(len(boards)):
        if k in won: continue
        b = boards[k]
        for xs in b:
            for i in range(len(xs)):
                if xs[i] == x:
                    xs[i] = -1
        if check_bingo(b):
            won.add(k)
            if len(won) == 1:
                print("Part one:", check_score(b, x))
            if len(won) == len(boards):
                print("Part two:", check_score(b, x))
