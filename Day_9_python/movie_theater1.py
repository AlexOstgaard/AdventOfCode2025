filename = input("Name of input-file: ")
input = []
with open(filename, "r") as file:
    input = file.read().split("\n")

all_tiles = []
for line in input:
    coordinate = line.split(",")
    all_tiles.append([int(coordinate[0]), int(coordinate[1])])

#There is surely a smarter way to exclude a lot of iterations, but I couldn't figure anything out.

largest_area = 0
for tile1 in all_tiles:
    for tile2 in all_tiles:
        curr_area = (abs(tile2[0] - tile1[0]) + 1) * (abs(tile2[1] - tile1[1]) + 1)
        if curr_area > largest_area:
            largest_area = curr_area

print(f"Largest area: {largest_area}")
