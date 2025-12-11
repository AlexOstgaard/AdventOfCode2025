from collections import deque


def count_paths(device_from, device_to, restrictions):

    queue = deque()
    queue.append(device_from)
    path_count = 0
   
    while len(queue) > 0:
        curr_device = queue.popleft()

        for output in dict_input[curr_device]:
            if output == device_to:
                path_count += 1
            elif output not in restrictions and output != 'out':
                queue.append(output)

    return path_count


def make_restrictions(device_from, restrictions_amount):

    queue = deque()
    queue.append(device_from)
    restrictions = set()

    while len(restrictions) < restrictions_amount and len(queue) > 0:

        curr_device = queue.popleft()

        for output in dict_input[curr_device]:
            if output != 'out':
                queue.append(output)
                restrictions.add(output)

    return restrictions


#main is down here

filename = input("Name of input-file: ")
input = []
dict_input = {}

with open(filename, "r") as file:

    input = file.read().split("\n")


for line in input:
    dict_input[line[:3]] = line.split(" ")[1:]



need_to_visit = ['fft', 'dac']

#Checking which comes first, fft or dac

queue = deque()
queue.append(need_to_visit[0])

from_device0_to_device1 = False

while len(queue) > 0 and from_device0_to_device1 == False:
    curr_device = queue.popleft()

    for output in dict_input[curr_device]:
        if output == need_to_visit[1]:
            from_device0_to_device1 = True

        elif output != 'out':
            queue.append(output)


if from_device0_to_device1:
    first = need_to_visit[0]
    second = need_to_visit[1]
else:
    first = need_to_visit[1]
    second = need_to_visit[0]

print("Found which device comes first!")

#Now, we make a list with restrictions all paths to the device we must visit first.

#The amount of restrictions we make can be anything, but it reduces the amount of elements we put in the queue by a lot!

#I came up with 200 by doing some testing
first_restrictions = make_restrictions(first, 500)

paths_to_first = count_paths('svr', first, first_restrictions)

print(f"Paths to {first}: {paths_to_first}")


#Now, paths from first to second
second_restrictions = make_restrictions(second, 500)
print("Made restrictions")

paths_to_second = count_paths(first, second, second_restrictions)

#print(f"Paths to {second} from {first}: {paths_to_second}")

#Final stretch is paths from second to out.

paths_out = count_paths(second, 'out', set())

print(f"Paths out from {second}: {paths_out}")

print(f"Sum of all paths that follow the decided route is {paths_to_first * paths_to_second * paths_out}")