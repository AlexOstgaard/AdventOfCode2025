from collections import deque

filename = input("Name of input-file: ")
input = []
dict_input = {}

with open(filename, "r") as file:

    input = file.read().split("\n")


for line in input:
    dict_input[line[:3]] = line.split(" ")[1:]

queue = deque()
queue.append('you')

paths = 0

while len(queue) > 0:
    curr_device = queue.popleft()
    for output in dict_input[curr_device]:
        if output == 'out':
            paths += 1
        else:
            queue.append(output)

print(f"All paths: {paths}")
