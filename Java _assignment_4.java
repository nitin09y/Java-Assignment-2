import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Book implements Comparable<Book> {
    int bookId; 
    String title, author, category; 
    boolean isIssued;
    
    public Book(int id, String t, String a, String c) {
        bookId = id; 
        title = t; 
        author = a; 
        category = c; 
        isIssued = false;
    }
    
    public void display() {
        System.out.printf("ID: %d | %s by %s | %s | %s\n", 
            bookId, title, author, category, isIssued ? "Issued" : "Available");
    }
    
    public int compareTo(Book b) { 
        return title.compareTo(b.title); 
    }
    
    public String toString() { 
        return bookId + "," + title + "," + author + "," + category + "," + isIssued; 
    }
}

class Member {
    int memberId; 
    String name, email; 
    List<Integer> issuedBooks = new ArrayList<>();
    
    public Member(int id, String n, String e) { 
        memberId = id; 
        name = n; 
        email = e; 
    }
    
    public void display() {
        System.out.printf("ID: %d | %s | %s | Books: %s\n", memberId, name, email, issuedBooks);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(memberId).append(",").append(name).append(",").append(email);
        for (int bookId : issuedBooks) {
            sb.append(",").append(bookId);
        }
        return sb.toString();
    }
}

class AuthorComparator implements Comparator<Book> {
    public int compare(Book b1, Book b2) { 
        return b1.author.compareTo(b2.author); 
    }
}

public class Main {
    private static Map<Integer, Book> books = new HashMap<>();
    private static Map<Integer, Member> members = new HashMap<>();
    private static int bookId = 1, memberId = 1;
    private static final String BOOKS_FILE = "books.txt", MEMBERS_FILE = "members.txt";
    
    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);
        
        while(true) {
            System.out.println("\n=== CITY LIBRARY SYSTEM ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member"); 
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Display All Books");
            System.out.println("8. Display All Members");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            
            String choice = sc.nextLine();
            
            switch(choice) {
                case "1": addBook(sc); break;
                case "2": addMember(sc); break;
                case "3": issueBook(sc); break;
                case "4": returnBook(sc); break;
                case "5": searchBooks(sc); break;
                case "6": sortBooks(sc); break;
                case "7": displayAllBooks(); break;
                case "8": displayAllMembers(); break;
                case "9": saveData(); System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }
    
    static void addBook(Scanner sc) {
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        if(title.isEmpty()) {
            System.out.println("Title cannot be empty!");
            return;
        }
        
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        if(author.isEmpty()) {
            System.out.println("Author cannot be empty!");
            return;
        }
        
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        if(category.isEmpty()) {
            System.out.println("Category cannot be empty!");
            return;
        }
        
        books.put(bookId, new Book(bookId, title, author, category));
        System.out.println("Book added successfully! ID: " + bookId);
        bookId++;
        saveData();
    }
    
    static void addMember(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        if(name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        if(email.isEmpty() || !email.contains("@")) {
            System.out.println("Invalid email!");
            return;
        }
        
        members.put(memberId, new Member(memberId, name, email));
        System.out.println("Member added successfully! ID: " + memberId);
        memberId++;
        saveData();
    }
    
    static void issueBook(Scanner sc) {
        System.out.print("Enter Member ID: ");
        try {
            int memberId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Book ID: ");
            int bookId = Integer.parseInt(sc.nextLine());
            
            if(!members.containsKey(memberId)) {
                System.out.println("Member not found!");
                return;
            }
            
            if(!books.containsKey(bookId)) {
                System.out.println("Book not found!");
                return;
            }
            
            Book book = books.get(bookId);
            Member member = members.get(memberId);
            
            if(book.isIssued) {
                System.out.println("Book is already issued!");
                return;
            }
            
            book.isIssued = true;
            member.issuedBooks.add(bookId);
            System.out.println("Book issued successfully to " + member.name);
            saveData();
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }
    
    static void returnBook(Scanner sc) {
        System.out.print("Enter Member ID: ");
        try {
            int memberId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Book ID: ");
            int bookId = Integer.parseInt(sc.nextLine());
            
            if(!members.containsKey(memberId)) {
                System.out.println("Member not found!");
                return;
            }
            
            if(!books.containsKey(bookId)) {
                System.out.println("Book not found!");
                return;
            }
            
            Book book = books.get(bookId);
            Member member = members.get(memberId);
            
            if(!book.isIssued) {
                System.out.println("Book was not issued!");
                return;
            }
            
            if(!member.issuedBooks.contains(bookId)) {
                System.out.println("This book is not issued to this member!");
                return;
            }
            
            book.isIssued = false;
            member.issuedBooks.remove(Integer.valueOf(bookId));
            System.out.println("Book returned successfully!");
            saveData();
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }
    
    static void searchBooks(Scanner sc) {
        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author"); 
        System.out.println("3. Category");
        System.out.print("Enter choice: ");
        
        String choice = sc.nextLine();
        System.out.print("Enter search term: ");
        String term = sc.nextLine().toLowerCase();
        
        boolean found = false;
        for(Book book : books.values()) {
            boolean match = false;
            if(choice.equals("1") && book.title.toLowerCase().contains(term)) {
                match = true;
            } else if(choice.equals("2") && book.author.toLowerCase().contains(term)) {
                match = true;
            } else if(choice.equals("3") && book.category.toLowerCase().contains(term)) {
                match = true;
            }
            
            if(match) {
                book.display();
                found = true;
            }
        }
        
        if(!found) {
            System.out.println("No books found!");
        }
    }
    
    static void sortBooks(Scanner sc) {
        System.out.println("Sort by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.print("Enter choice: ");
        
        String choice = sc.nextLine();
        List<Book> bookList = new ArrayList<>(books.values());
        
        if(choice.equals("1")) {
            Collections.sort(bookList);
        } else if(choice.equals("2")) {
            Collections.sort(bookList, new AuthorComparator());
        } else if(choice.equals("3")) {
            Collections.sort(bookList, new Comparator<Book>() {
                public int compare(Book b1, Book b2) {
                    return b1.category.compareTo(b2.category);
                }
            });
        } else {
            System.out.println("Invalid choice!");
            return;
        }
        
        System.out.println("Sorted Books:");
        for(Book book : bookList) {
            book.display();
        }
    }
    
    static void displayAllBooks() {
        if(books.isEmpty()) {
            System.out.println("No books in library!");
        } else {
            System.out.println("All Books (" + books.size() + "):");
            for(Book book : books.values()) {
                book.display();
            }
        }
    }
    
    static void displayAllMembers() {
        if(members.isEmpty()) {
            System.out.println("No members registered!");
        } else {
            System.out.println("All Members (" + members.size() + "):");
            for(Member member : members.values()) {
                member.display();
            }
        }
    }
    
    static void loadData() {
        // Load books
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length == 5) {
                    Book book = new Book(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                    book.isIssued = Boolean.parseBoolean(parts[4]);
                    books.put(book.bookId, book);
                    bookId = Math.max(bookId, book.bookId + 1);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("No existing book data found. Starting fresh.");
        }
        
        // Load members
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 3) {
                    Member member = new Member(Integer.parseInt(parts[0]), parts[1], parts[2]);
                    for(int i = 3; i < parts.length; i++) {
                        member.issuedBooks.add(Integer.parseInt(parts[i]));
                    }
                    members.put(member.memberId, member);
                    memberId = Math.max(memberId, member.memberId + 1);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("No existing member data found. Starting fresh.");
        }
    }
    
    static void saveData() {
        // Save books
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE));
            for(Book book : books.values()) {
                writer.write(book.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving books!");
        }
        
        // Save members
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE));
            for(Member member : members.values()) {
                writer.write(member.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving members!");
        }
    }
}