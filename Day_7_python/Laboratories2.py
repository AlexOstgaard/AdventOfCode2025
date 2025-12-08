filename = input("Name of input-file: ")
content = []

with open(filename, "r") as file:
    content_lines = file.read().split("\n")


len_lines = len(content_lines[0])

#Making a dict where I have the index of each beam as key, and value is the amount of ways to get to that beam.
beams = {}

for symbol_index in range(len_lines):
    if content_lines[0][symbol_index] == "S":
        beams[symbol_index] = 1

for line_index in range(1, len(content_lines)):
    curr_line = content_lines[line_index]
    
    for symbol_index in range(len_lines):
        if curr_line[symbol_index] == "^" and symbol_index in beams:
            if symbol_index - 1 not in beams:
                beams[symbol_index - 1] = 0
            beams[symbol_index - 1] += beams[symbol_index]
            if symbol_index + 1 not in beams:
                beams[symbol_index + 1] = 0
            beams[symbol_index + 1] += beams[symbol_index]
            del beams[symbol_index]
            

    previous_line = curr_line

timelines_count = 0
for beam in beams:
    timelines_count += beams[beam]
print(timelines_count)