filename = input("Filename for input: ")

with open(filename, "r") as file:

    banks = []
    for line in file:
        banks.append(line.strip())
    
total_output = 0

for bank in banks:

    max_index = len(bank) - 2
    max_second_index = len(bank) - 1

    for i in range(len(bank) - 3, -1, -1):
        
        if int(bank[i]) >= int(bank[max_index]):
            if int(bank[max_index]) > int(bank[max_second_index]):
                max_second_index = max_index
            max_index = i
    
    total_output += int(bank[max_index] + bank[max_second_index])

print(total_output)