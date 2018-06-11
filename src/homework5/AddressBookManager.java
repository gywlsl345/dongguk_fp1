import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class AddressBook {
    private String name;
    private String phone;

    public AddressBook(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String toString() {
        return String.format("%s(%s)", name, phone);
    }
}

public class AddressBookManager {
    private Map<String, AddressBook> books = null;

    public AddressBookManager() {
        books = new HashMap<String, AddressBook>();
    }

    public void add(String name, String phone) {
        AddressBook book = new AddressBook(name, phone);
        books.put(name, book);
    }

    public AddressBook remove(String name) {
        AddressBook book = findByName(name);
        if (book != null) {
            books.remove(name);
        }

        return book;
    }

    public void printAll() {
        for (String key : books.keySet()) {
            AddressBook book = books.get(key);
            System.out.println(book.toString());
        }
    }

    public AddressBook findByName(String name) {
        //Implement to find book By Name
        AddressBook book = null;
        for (String key : books.keySet()) {
            if (books.containsKey(name)) {
                book = books.get(name);
            }

        } return book;
    }

    public boolean save(String path) {
        //Implement save method
        try {
            FileWriter writer = new FileWriter(path);
            String str = null;
            for(String key : books.keySet()) {
                AddressBook book = books.get(key);
                String [] parts = book.toString().split("\\(|\\)");
                if(parts.length == 2) {
                    str = parts[0] + " " + parts[1];
                    writer.write(str + "\r\n");
                }
            }
            writer.close();
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    public boolean load(String path) {
        //Implement load method
        String str = null;
        books.clear();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            str = null;
            while((str=reader.readLine()) != null) {
                String [] parts = str.split(" ");
                if(parts.length == 2) {
                    add(parts[0], parts[1]);
                }
                else {
                    System.out.println("Invalid format");
                }
            }
            fileReader.close();
            reader.close();
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    public static void showMenu() {
        System.out.println("===== Menu =====");
        System.out.println("1. Add Address");
        System.out.println("2. PrintAll");
        System.out.println("3. Find By Name");
        System.out.println("4. Remove By Name");
        System.out.println("5. Save");
        System.out.println("6. Load");
        System.out.println("7. Quit");
    }

    public static int getMenu(Scanner sc) {
        return Integer.parseInt(sc.nextLine().trim());
    }

    public static void main(String...args) {
        Scanner sc = new Scanner(System.in);
        int count = 0;

        AddressBookManager bookManager = new AddressBookManager();

        while(true) {
            showMenu();
            int menu = getMenu(sc);
            if (menu == 1) {
                String name = sc.nextLine();
                String phone = sc.nextLine();
                bookManager.add(name, phone);
            } else if (menu == 2) {
                bookManager.printAll();
            } else if (menu == 3) {
                String name = sc.nextLine();
                AddressBook book = bookManager.findByName(name);
                if (book != null) {
                    System.out.println("Find: " + book.toString());
                } else {
                    System.out.println("There is no address : " + name);
                }
            } else if (menu == 4) {
                String name = sc.nextLine();
                AddressBook book = bookManager.remove(name);
                if (book != null) {
                    System.out.println("Delete: " + book.toString());
                } else {
                    System.out.println("There is no address : " + name);
                }
            } else if (menu == 5) {
                if (bookManager.save("user.txt") == false) {
                    System.out.println("Fail to save");
                }
            } else if (menu == 6) {
                if (bookManager.load("user.txt") == false) {
                    System.out.println("Fail to load");
                }
            } else if (menu == 7) {
                break;
            }
        }
    }
}