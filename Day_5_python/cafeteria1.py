filename = input("Filename for input: ")

with open(filename, "r") as file:
    all_ids = file.read().split("\n\n")
    id_ranges = all_ids[0].split("\n")
    food_ids = all_ids[1].split("\n")

fresh_count = 0

for id in food_ids:
    available = False
    for range in id_ranges:
        splitted_range = range.split("-")
        start = range[0]
        end = range[1]

        if int(id) >= int(splitted_range[0]) and int(id) <= int(splitted_range[1]):
            available = True
    if available:
        fresh_count += 1

print(fresh_count)