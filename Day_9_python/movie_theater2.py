filename = input("Name of input-file: ")
input = []
with open(filename, "r") as file:
    input = file.read().split("\n")

all_tiles = []
for line in input:
    coordinate = line.split(",")
    all_tiles.append([int(coordinate[0]), int(coordinate[1])])

#In this problem, my time complexity is O(n^3).
# Couldn't come up with a better solution, and I'm not happy about it.

# I want to know which "side" the green tiles are on, by traversing the edges, and check what the degree is.

directions = ["L", "U", "R", "D"] #Left, Up, Right and Down

def check_direction(start, end):
    direction = ""
    if end[0] > start[0]:
        direction = "R"
    elif end[0] < start[0]:
        direction = "L"
    elif end[1] > start[1]:
        direction = "D"
    else:
        direction = "U"
    return direction


degree = 0
previous_direction = check_direction(all_tiles[-1], all_tiles[0])

green_on_right_side = False
for tile_index in range(len(all_tiles) - 1):
    tile1 = all_tiles[tile_index]
    tile2 = all_tiles[tile_index + 1]
    direction = check_direction(tile1, tile2)

    if (direction == "L" and previous_direction == "D") or directions.index(direction) > directions.index(previous_direction):
        degree += 1
    else:
        degree -= 1

    previous_direction = direction
    
if degree > 0:
    green_on_right_side = True


largest_area = 0

for tile1_index in range(len(all_tiles) - 1):
    for tile2_index in range(tile1_index + 1, len(all_tiles) + tile1_index):

        tile1 = all_tiles[tile1_index]
        tile2 = all_tiles[tile2_index % len(all_tiles)]

        if tile1[0] > tile2[0]:
            largest_x = tile1
            smallest_x = tile2
        else:
            largest_x = tile2
            smallest_x = tile1

        if tile1[1] > tile2[1]:
            largest_y = tile1
            smallest_y = tile2
        else:
            largest_y = tile2
            smallest_y = tile1

        curr_area = (largest_x[0] - smallest_x[0] + 1) * (largest_y[1] - smallest_y[1] + 1)


        if curr_area > largest_area:

            #Checking if any other tiles "interrupt" the rectangle by existing in the rectangle
            interrupted = False
            for tile3 in all_tiles:
                if tile3[0] > smallest_x[0] and tile3[0] < largest_x[0] and tile3[1] > smallest_y[1] and tile3[1] < largest_y[1]:
                    interrupted = True
            
            if not interrupted:

            
            #Also check if the rectangle is on the "correct side".
                correct_side = True

                degree = 0
                previous_direction = check_direction(tile1, all_tiles[(tile1_index + 1) % len(all_tiles)])
                direction = None

                for tile_checking_index in range(tile1_index + 1, tile2_index - 1):
                    check_tile1 = all_tiles[tile_checking_index % len(all_tiles)]
                    check_tile2 = all_tiles[(tile_checking_index + 1) % len(all_tiles)]

                    direction = check_direction(check_tile1, check_tile2)

                    if (direction == "L" and previous_direction == "D") or directions.index(direction) > directions.index(previous_direction):
                        degree += 1
                    else:
                        degree -= 1

                    previous_direction = direction


                if ((degree > 0) == green_on_right_side) and degree != 0:
                    largest_area = curr_area



print(f"Largest area: {largest_area}")


#Giving up... I have to study for my exams. This code is incorrect.
