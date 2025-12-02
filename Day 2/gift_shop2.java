import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

class gift_shop2 {
    public static void main(String args[]) {

        String input_ids = "";
        HashMap<Integer, HashSet<Long>> possible_patterns_by_len = new HashMap<>();

        //For this problem, the HashMap has to include every combination of 1s and 0s that correlate to 
        // a pattern with any factor of the length of the number as the key.
        for (int i = 1; i < 20; i += 1) { //I picked 20, since I can't have Long-numbers longer than 1111111111111111111.

            HashSet<Long> patterns_per_length = new HashSet<>();

            for (int j = 2; j <= i; j+= 1) {
                if (i % j == 0) {
                    long value = Long.parseLong(("0".repeat(i/j-1) + "1").repeat(j));
                    patterns_per_length.add(value);
                }
            }
            possible_patterns_by_len.put(i,patterns_per_length);
        }
        
        //Read input-file
        try {
            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);

            input_ids = scanner.nextLine();
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.print(e);
        }
        //Create tuples
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
            total_sum += tuple.sum_invalids(possible_patterns_by_len);
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

    public long sum_invalids(HashMap<Integer, HashSet<Long>> possible_patterns_by_len) {
        long sum = 0;
        for (long curr_id = x; curr_id < (y + 1); curr_id++) {
            int id_length = Long.toString(curr_id).length();
            boolean invalid = false;

            for (Long pattern : possible_patterns_by_len.get(id_length)) {
                if (curr_id % pattern == 0) {
                    invalid = true;
                }
            }
            if (invalid) {
                    sum += curr_id;
            }
        }
        return sum;
    }
}