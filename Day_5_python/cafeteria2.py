filename = input("Filename for input: ")

with open(filename, "r") as file:
    all_ids = file.read().split("\n\n")
    id_ranges = all_ids[0].split("\n")

fresh_id_count = 0
range_arrays = []
for id_range in id_ranges:
    splitted_range = id_range.split("-")
    range_arrays.append([int(splitted_range[0]), int(splitted_range[1])])

sorted_by_start = sorted(range_arrays, key = lambda x: x[0])

index = 0

while index < len(sorted_by_start):

    curr_min = sorted_by_start[index][0]
    curr_max = sorted_by_start[index][1]

    while index < len(sorted_by_start) - 1 and curr_max >= sorted_by_start[index + 1][0] - 1:
        curr_max = max(curr_max, sorted_by_start[index + 1][1])
        index += 1
        
    fresh_id_count += curr_max - curr_min + 1

    index += 1

print(index)  
print(fresh_id_count)
