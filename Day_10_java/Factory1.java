import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.HashSet;


class Factory1 {
    public static void main(String[] args) {
        
        Scanner terminal_scanner = new Scanner(System.in);

        System.out.println("What is the name of the input-file: ");
        String filename = terminal_scanner.nextLine();

        terminal_scanner.close();

        HashSet<String> input = new HashSet<>();

        try {
            Scanner input_scanner = new Scanner(new File(filename));
            while (input_scanner.hasNextLine()) {
                input.add(input_scanner.nextLine());
            }
            input_scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find that file!");
        }

        Machine[] all_machines = new Machine[input.size()];
        int machine_index = 0;

        for (String line : input) {
            String[] content = line.split(" ");

            Machine new_machine = new Machine(content);
            all_machines[machine_index] = new_machine;

            machine_index ++;
        }
        
        int sum_totals = 0;

        for (Machine machine : all_machines) {
            sum_totals += machine.fewest_total_presses();
        }
        System.out.println(sum_totals);

    }
}

class Machine {

    String light_diagram;
    HashSet<int[]> buttons;

    Machine(String[] content) {
        light_diagram = content[0].substring(1, content[0].length() - 1);

        buttons = new HashSet<>();

        for (int button_index = 0; button_index < content.length - 2; button_index++) {
            String button_string = content[button_index + 1];
            String[] buttons_as_strings = (button_string.substring(1, button_string.length() - 1).split(","));
            int[] button = new int[buttons_as_strings.length];

            for (int s_index = 0; s_index < buttons_as_strings.length; s_index++) {
                button[s_index] = Integer.parseInt(buttons_as_strings[s_index]);
            }
            buttons.add(button);
        }
    }

//Based on the example used in the explanation of the problem, it seemed that you never have to press the same button twice. 
// Makes sense, since pressing a button a second time would just invert the first press. 
    int fewest_total_presses() {

        String lights_toggled = "";
        for (int i = 0; i < light_diagram.length(); i++) {
            lights_toggled += ".";
        }
        int fewest_total = 0;
        boolean correct = false;
        HashSet<String> all_attempts = new HashSet<>();

        all_attempts.add(lights_toggled);

        while (!correct) {

            fewest_total ++;
            HashSet<String> new_attempts = new HashSet<>();
            //Make new attempts
            for (String attempt : all_attempts) {
                new_attempts = loop_and_try(attempt, new_attempts);
            }

            //Loop through attempts and check if any of them works.
            for (String attempt : new_attempts) {
                if (check_lights(attempt)) {
                    correct = true;
                }
            }
            all_attempts = new_attempts;
        }
        return fewest_total;
    }


    boolean check_lights(String lights_toggled) {
        return (lights_toggled.equals(light_diagram));
    }

    HashSet<String> loop_and_try(String curr_attempt, HashSet<String> new_attempt) {
        
        for (int[] button : buttons) {
            StringBuilder new_button_press = new StringBuilder(curr_attempt);

            for (int light : button) {
                if (new_button_press.charAt(light) == '.') {
                    new_button_press.setCharAt(light, '#');
                } else {
                    new_button_press.setCharAt(light, '.');
                }
            }
            new_attempt.add(new_button_press.toString());
        }
        return new_attempt;
    }


    String get_light_diagram() {
        return light_diagram;
    }

    HashSet<int[]> get_buttons() {
        return buttons;
    }
}