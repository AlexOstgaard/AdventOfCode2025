import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

class printing_department1{

    public static void main(String args[]) {

        // Read from input-file
        ArrayList<String> input = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke en fil med det navnet!");
        }

        //Make a grid that contains objects of the class 'Position', which relates to 
        // every position in the grid that is either a paper roll or something else.

        Position[][] grid = new Position[input.size()][input.get(0).length()];

        for (int line_index = 0; line_index < input.size(); line_index++) {
            String line = input.get(line_index);

            for (int char_index = 0; char_index < line.length(); char_index++) {
                grid[line_index][char_index] = new Position(line.charAt(char_index));
            }
        }

        boolean give_up = false;
        int total_rolls = 0;
        while (!give_up) {
            
            ArrayList<Position> removable = new ArrayList<>();

            for (int line_index = 0; line_index < grid.length; line_index++) {
                Position[] line = grid[line_index];
                for (int pos_index = 0; pos_index < line.length; pos_index++) {
                    Position curr_pos = line[pos_index];
                    ArrayList<Position> neighbours = new ArrayList<>();

                    //Code looks disguisting, but it safely makes a list of all neighbours to a position
                    for (int y = line_index -1; y < line_index +2 ; y++) {
                        for (int x = pos_index - 1; x < pos_index + 2; x ++) {
                            if (y > -1 && y < grid.length && x > -1 && x < line.length) {
                                if (!(x == pos_index && y == line_index)) {
                                    neighbours.add(grid[y][x]);
                                }
                            }
                        }
                    }
                    if (curr_pos.check_removable(neighbours)) {
                        removable.add(curr_pos);
                    }
                }
            }
            int available_rools_count = removable.size();
            for (Position curr_pos : removable) {
                curr_pos.remove_paper();
            }
            System.out.println("Rolls removed this cycle: " + available_rools_count);
            total_rolls += available_rools_count;
            if (available_rools_count == 0) {
                System.out.println("Cancelling the search. Total rolls removed: " + total_rolls);
                give_up = true;
            }
        }
    }
}

class Position {

    char symbol;

    Position(char symbol) {
        this.symbol = symbol;
    }

    boolean check_removable(ArrayList<Position> neighbours) {
        if (symbol != '@') {
            return false;
        }
        int sum_adjacent_rolls = 0;
        for (Position neighbour : neighbours) {
            if (neighbour.get_Symbol() == '@') {
                sum_adjacent_rolls++;
            }
        }
        return (sum_adjacent_rolls < 4);
    }

    char get_Symbol() {
        return symbol;
    }

    void remove_paper() {
        symbol = '.';
    }
}