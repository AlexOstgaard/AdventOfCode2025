import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class gift_shop1 {
    public static void main(String args[]) {

        String input_ids = "";
        HashMap<Integer, Integer> possible_patterns = new HashMap<>();

        //I create a HashMap where the key is the length of a number, and the value is 
        // the number that length has to be divisible by, in order to match the pattern for invalid ID's.
        for (int i = 2; i < 19; i += 2) { //I picked 19, because larger numbers has to be Long-objects.
            int value = Integer.parseInt("1" + "0".repeat((i/2)-1) + "1");
            possible_patterns.put(i,value);
        }
        

        try {
            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);

            input_ids = scanner.nextLine();
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.print(e);
        }

        String[] id_strings = (input_ids.split(","));
        
        Id_tuple[] tuples = new Id_tuple[id_strings.length];

        for (int i = 0; i < id_strings.length; i++) {

            String[] tuple = id_strings[i].split("-");

            long x = Long.parseLong(tuple[0]);
            long y = Long.parseLong(tuple[1]);

            tuples[i] = new Id_tuple(x, y);
        }

        long total_sum = 0;

        for (Id_tuple tuple : tuples) {
            total_sum += tuple.sum_invalids(possible_patterns);
        }
        System.out.println(total_sum);
    }
}

class Id_tuple {
    long x;
    long y;
    public Id_tuple(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long sum_invalids(HashMap<Integer, Integer> possible_patterns) {
        long sum = 0;
        for (long curr_id = x; curr_id <= y; curr_id++) {
            int id_length = Long.toString(curr_id).length();

            if (id_length % 2 == 0) {
                if (curr_id % possible_patterns.get(id_length) == 0) {
                    sum += curr_id;
                }
            }
        }
        return sum;
    }
}

