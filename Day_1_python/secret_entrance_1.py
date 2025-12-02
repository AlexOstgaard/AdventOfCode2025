filename = input("Filename: ")

with open(filename, "r") as f:
    rotations = f.read().split("\n")


zeroes = 0
dial = 50

directions = {"L": -1, "R": 1}

for rotation in rotations:
    dial += directions[rotation[0]] * int(rotation[1:])
    dial = dial % 100
    if dial == 0:
        zeroes += 1
print(zeroes)