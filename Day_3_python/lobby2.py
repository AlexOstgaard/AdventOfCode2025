filename = input("Filename for input: ")

with open(filename, "r") as file:

    banks = []
    for line in file:
        banks.append(line.strip())
    
total_output = 0
len_joltage = 12

for bank in banks:
    #c_j_i = current_joltage_indexes
    c_j_i = list(range(len(bank)-12, len(bank)))


    for i in range(len(bank) - len_joltage - 1, -1, -1):
        
        if int(bank[i]) >= int(bank[c_j_i[0]]): #Find a value that replaces the first digit in current_joltage
            vacant_index = c_j_i[0]
            for joltage_index in range(1, len_joltage): #Loop through all the other digits and replace them if possible
                if int(bank[c_j_i[joltage_index]]) <= int(bank[vacant_index]):
                    temp = vacant_index
                    vacant_index = c_j_i[joltage_index]
                    c_j_i[joltage_index] = temp
                else:
                    break
            c_j_i[0] = i
            
    joltage = ""
    for i in c_j_i:
        joltage += bank[i]
    joltage = int(joltage)

    total_output += joltage

print(total_output)