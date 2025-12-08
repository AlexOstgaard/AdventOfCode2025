import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;


class Playground2 {
    public static void main(String args[]){

        Scanner input_scanner = new Scanner(System.in);

        System.out.println("Name of input_file: ");
        String filename = input_scanner.nextLine();

        input_scanner.close();

        ArrayList<String> input = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(filename));
            
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find a file with that name!");
        }

        int len_input = input.size();

        ArrayList<Circuit> all_circuits = new ArrayList<>();
        ArrayList<Junction_box> all_boxes = new ArrayList<>();


        //Making junction boxes and circuits
        for (int line_index = 0; line_index < len_input; line_index++) {
            String[] coordinates_strings = input.get(line_index).split(",");
            int[] coordinates_ints = new int[3];

            for (int i = 0; i < 3; i++) {
                coordinates_ints[i] = Integer.parseInt(coordinates_strings[i]);
            }
            Junction_box new_box = new Junction_box(coordinates_ints);
            Circuit new_circuit = new Circuit(new_box);
            new_box.change_circuit(new_circuit);

            all_boxes.add(new_box);
            all_circuits.add(new_circuit);
        }
        
        //Making a priority_queue with connections
        PriorityQueue<Connection> not_connections_yet = new PriorityQueue<>((a, b) -> Double.compare(a.get_distance(), b.get_distance()));
        
        for (int box_index1 = 0; box_index1 < len_input - 1; box_index1++) {
            for (int box_index2 = box_index1 + 1; box_index2 < len_input; box_index2++) {
                Junction_box box1 = all_boxes.get(box_index1);
                Junction_box box2 = all_boxes.get(box_index2);
                Connection connection = new Connection(new Junction_box[] {box1, box2}, box1.get_dist(box2));
                not_connections_yet.add(connection);
            }
        }
        
        //This is me attempting to implement Kruskals algorithm:
        Connection final_connection = null;

        while (final_connection == null) {

            Connection shortest_connection = not_connections_yet.poll();

            Junction_box box1 = shortest_connection.get_pair()[0];
            Junction_box box2 = shortest_connection.get_pair()[1];

            Circuit circuit1 = box1.get_circuit();
            Circuit circuit2 = box2.get_circuit();

            if (circuit1 != circuit2) {
                circuit1.connect(circuit2);
                all_circuits.remove(circuit2);
            }
            not_connections_yet.remove(shortest_connection);

            if (all_circuits.size() == 2) {

                final_connection = not_connections_yet.poll();

                while (final_connection.same_circuit()) {
                    final_connection = not_connections_yet.poll();
                }
            }       
        }
        //Finally, print out the result
        
        Junction_box[] box_pair = final_connection.get_pair();

        long box_1_x = box_pair[0].get_coords()[0];
        long box_2_x = box_pair[1].get_coords()[0];

        long result = box_1_x * box_2_x;

        System.out.println("Result: " + result);
    }
}


class Junction_box {

    int[] coordinates = new int[3];
    Circuit circuit;

    Junction_box(int[] coordinates) {
        this.coordinates = coordinates;
    }

    int[] get_coords() {
        return coordinates;
    }
    Circuit get_circuit() {
        return circuit;
    }
    void change_circuit(Circuit new_circuit) {
        circuit = new_circuit;
    }

    double get_dist(Junction_box other_box) {
        int[] other_coords = other_box.get_coords();
        return Math.sqrt(Math.pow((other_coords[0] - coordinates[0]), 2) + Math.pow((other_coords[1] - coordinates[1]), 2) + Math.pow((other_coords[2] - coordinates[2]), 2));
    }
}

class Circuit {

    ArrayList<Junction_box> boxes = new ArrayList<>();

    Circuit(Junction_box start_box) {
        boxes.add(start_box);
    }
    void connect(Circuit new_circuit) {
        for (Junction_box box : new_circuit.get_boxes()) {
            boxes.add(box);
            box.change_circuit(this);
        }
    }
    ArrayList<Junction_box> get_boxes() {
        return boxes;
    }
    int get_size() {
        return boxes.size();
    }
}

class Connection {

    Junction_box[] box_pair;
    Double distance;

    Connection(Junction_box[] box_pair, Double distance) {
        this.box_pair = box_pair;
        this.distance = distance;
    }

    Double get_distance() {
        return distance;
    }
    Junction_box[] get_pair() {
        return box_pair;
    }
    boolean same_circuit() {
        return (box_pair[0].get_circuit() == box_pair[1].get_circuit());
    }
}