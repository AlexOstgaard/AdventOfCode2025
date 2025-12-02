filename = input("Filename: ")

with open(filename, "r") as f:
    rotations = f.read().split("\n")


zeroes = 0
dial = 50

directions = {"L": -1, "R": 1}

for rotation in rotations:
    
    previous_dial = dial

    dial += directions[rotation[0]] * int(rotation[1:])
    if dial > 99:
        zeroes += dial // 100

    elif dial < 1:
        zeroes += (dial * -1 // 100)

        if previous_dial != 0:
            zeroes += 1

    dial = dial % 100

print(zeroes)