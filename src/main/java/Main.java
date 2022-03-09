import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

//        TODO: clean up hasherimpl for sure

        UserDB db = new UserDB();

        Scanner scanner = new Scanner(System.in);

        boolean check = false;
        while (!check) {
            System.out.println("Press 1 to register, 2 to login, 3 to exit");
            String option = scanner.nextLine();

            String email = "";
            String password = "";

            switch (option) {
                case "1":
                    getEmail(scanner);
                    getPassword(scanner);
                    db.register(email, password);
                    check = true;
                    break;
                case "2":
                    getEmail(scanner);
                    getPassword(scanner);
                    db.login(email,password);
                    check = true;
                    break;
                case "3":
                    check = true;
                    break;
                default:
                    System.out.println("Follow the damn instructions!!!");
            }
        }

        System.exit(0);


    }

    private static String getEmail(Scanner scanner) {

        String email = "";
        while (!email.contains("@")) {
            System.out.println("Email address:");
            email = scanner.nextLine();
        }
        return email;
    }

    private static String getPassword(Scanner scanner) {

        String password = "";
        while (password.length() < 1) {
            System.out.println("Password:");
            password = scanner.nextLine();
        }
        return password;
    }
}
