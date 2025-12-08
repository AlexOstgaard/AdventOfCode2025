import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

class Trash_Compactor2 {

    //This is very similar to Trash_Compactor1, but with the numbers being different.
    //I have to be careful that not only every number, but every whitespace is saved.
    //In order to do so, I can't make lists of number anymore, and make much more detailed lists.
    public void main(String args[]) {

        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(args[0])); 

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the input file!");
        }

        HashMap<Integer, String> columns = new HashMap<>();

        int len_input = lines.get(0).length();

        for (Integer i = 0; i < len_input; i++) {
            columns.put(i, "");
        }

        //The columns-hashmap is still usefull in Trash_Compactor2

        for (String line : lines) {
            int horizontal_index = 0;

            for (int index = 0; index < len_input; index++) {
                char character = line.charAt(index);
                String current = columns.get(horizontal_index);
                current += character;
                columns.put(horizontal_index, current); 
                horizontal_index += 1;
            }
        }


        //The input we use for making Math_Problem-objects is different this time.

        ArrayList<Math_Problem> math_problems = new ArrayList<>();

        for (int column_key = 0; column_key < len_input; column_key++) {

            boolean isSpace = false;

            ArrayList<String> numbers = new ArrayList<>();
            
            while (!isSpace && column_key < len_input) {

                String column = columns.get(column_key);
                if (column.trim().isEmpty()) {
                    isSpace = true;
                     
                } else {
                    numbers.add(column);
                    column_key ++; 
                }
            }
            math_problems.add(new Math_Problem(numbers));
        }

        //Now finally, we can solve all the problems, and get a result.
        long grand_total = 0;

        for (Math_Problem problem : math_problems) {
            if (problem.get_operation() == '+') {
                grand_total += problem.sum();
            } 
            else if (problem.get_operation() == '*') {
                grand_total += problem.multiply();
            }
        }
        System.out.println(grand_total);
    }
}

class Math_Problem {

    char operation = ' ';
    int[] numbers;

//Our input this time is all the columns included in the problem, including white-spaces.
//Making numbers and correct operation out of the columns in the constructor below.

    Math_Problem(ArrayList<String> column_strings) {
        numbers = new int[column_strings.size()];
        for (int column_index = 0; column_index < column_strings.size(); column_index++) {

            String curr_column = column_strings.get(column_index);
            
            if (operation == ' ') {
                operation = curr_column.charAt(curr_column.length()-1);
            }
            numbers[column_index] = Integer.parseInt(curr_column.substring(0, curr_column.length()-1).trim());
        }
    }

    char get_operation() {
        return operation;
    }

    int sum() {
        int sum_numbers = 0;
        for (int number : numbers) {
            sum_numbers += number;
        }
        return sum_numbers;
    }

    long multiply() {
        long multi = 1;
        for (int number : numbers) {
            multi = multi * number;
        }
        return multi;
    }
}
