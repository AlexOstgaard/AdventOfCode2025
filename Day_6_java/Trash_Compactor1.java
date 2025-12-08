import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

class Trash_Compactor1 {


    public void main(String args[]) {

        ArrayList<String[]> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(args[0])); 

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().split(" +")); //Important that " +" splits on all spaces!!
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the input file!");
        }

        HashMap<Integer, String[]> columns = new HashMap<>();

        int len_input = lines.get(0).length;
        int height_input = lines.size();

        for (Integer i = 0; i < len_input; i++) {
            columns.put(i, new String[height_input]);
        }

        //First, I turn the horizontal oriented ArrayList into a vertical hashmap. 
        // This makes it easier to create the Math_Problem objects I want to use.

        int vertical_index = 0;

        for (String[] line : lines) {
            int horizontal_index = 0;

            for (String element : line) {
                columns.get(horizontal_index)[vertical_index] = element;
                horizontal_index += 1;
            }
            vertical_index += 1;
        }


        //Then, I make an array with all the 'Math_Problem'-objects.

        Math_Problem[] math_problems = new Math_Problem[len_input];

        for (int column_key = 0; column_key < len_input; column_key++) {
            int[] numbers = new int[height_input - 1];
            
            for (int i = 0; i < height_input - 1; i++) {
                numbers[i] = (Integer.parseInt(columns.get(column_key)[i]));
            }
            math_problems[column_key] = new Math_Problem(columns.get(column_key)[height_input-1].charAt(0), numbers);
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

    char operation;
    int[] numbers;

    Math_Problem(char operation, int[] numbers) {
        this.numbers = numbers;
        this.operation = operation;
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