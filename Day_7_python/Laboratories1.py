filename = input("Name of input-file: ")
content = []

with open(filename, "r") as file:
    content_lines = file.read().split("\n")


splits_count = 0
len_lines = len(content_lines[0])

previous_line = content_lines[0]

for symbol_index in range(len_lines):
    if previous_line[symbol_index] == "S":
        previous_line = previous_line[: symbol_index] + "|" + previous_line[symbol_index + 1:]

for line_index in range(1, len(content_lines)):
    curr_line = content_lines[line_index]
    
    for symbol_index in range(len_lines):
        if previous_line[symbol_index] == "|":

            if curr_line[symbol_index] == "^":
                splits_count += 1
                curr_line = curr_line[:symbol_index - 1] + "|^|" + curr_line[symbol_index + 2:]
            else:
                curr_line = curr_line[:symbol_index] + "|" + curr_line[symbol_index + 1:]
    previous_line = curr_line
        
print(splits_count)