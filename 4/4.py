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
    print(result, last)
    return result * last

broken = False
for x in bingonums:
    if broken: break
    for b in boards:
        for xs in b:
            for i in range(len(xs)):
                if xs[i] == x:
                    xs[i] = -1
        if check_bingo(b):
            broken = True
            print("win at this board")
            for xs in b:
                print(*xs)
            print("Score", check_score(b, x))
            break
                
    
