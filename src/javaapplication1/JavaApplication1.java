package javaapplication1;

import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private int year;
    private String summary;

    public Book(String title, int year, String summary) {
        this.title = title;
        this.year = year;
        this.summary = summary;
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getSummary() { return summary; }

    @Override
    public String toString() {
        return String.format("[%d] Title: %-20s", year, title);
    }
}

class SmartLibSystem {
    private ArrayList<Book> shelf = new ArrayList<>();

    public void addBook(Book b) { shelf.add(b); }

    public void sortByYear() {
        if (shelf.size() > 1) quickSort(0, shelf.size() - 1);
    }

    private void quickSort(int start, int end) {
        if (start < end) {
            int p = partition(start, end);
            quickSort(start, p - 1);
            quickSort(p + 1, end);
        }
    }

    private int partition(int start, int end) {
        int pivotYear = shelf.get(start).getYear();
        int left = start, right = end;
        while (left < right) {
            while (left < right && shelf.get(right).getYear() >= pivotYear) right--;
            if (left < right) swap(left, right);
            while (left < right && shelf.get(left).getYear() <= pivotYear) left++;
            if (left < right) swap(left, right);
        }
        return left;
    }

    private void swap(int i, int j) {
        Book temp = shelf.get(i);
        shelf.set(i, shelf.get(j));
        shelf.set(j, temp);
    }

    public void getConcordance(String keyword, int width) {
        System.out.println("\n--- Concordance Analysis Result ---");
        int total = 0;
        for (Book b : shelf) {
            String text = b.getSummary();
            int idx = text.indexOf(keyword);
            while (idx != -1) {
                total++;
                String left = "";
                for (int i = idx - width; i < idx; i++) left += (i < 0) ? " " : text.charAt(i);
                String right = "";
                int rStart = idx + keyword.length();
                for (int i = rStart; i < rStart + width; i++) right += (i >= text.length()) ? " " : text.charAt(i);
                System.out.printf("[%d] %6.10s [%s] %-10s (Book: %s)\n", total, left, keyword, right, b.getTitle());
                idx = text.indexOf(keyword, idx + 1);
            }
        }
        if (total == 0) System.out.println("Keyword not found in library.");
    }

    public void showShelf() {
        System.out.println("\n--- Library Shelf Status ---");
        for (Book b : shelf) System.out.println(b.toString());
    }
}

public class JavaApplication1 {
    public static void main(String[] args) {
        SmartLibSystem myLib = new SmartLibSystem();
        Scanner sc = new Scanner(System.in);
        
        myLib.addBook(new Book("Java Starter", 2024, "Java is a language. Java is OOP. Learn Java now!"));
        myLib.addBook(new Book("Algorithm Pro", 2021, "QuickSort is efficient. Algorithm logic is fun."));
        myLib.addBook(new Book("Python Data", 2023, "Python is simple. Many use Python for data."));

        boolean exit = false;
        while (!exit) {
            System.out.println("\n****************************************");
            System.out.println("* SmartLib Management System           *");
            System.out.println("****************************************");
            System.out.println("1. Sort & Show Books | 2. Search Keyword | 3. Exit");
            System.out.print("Please enter choice: ");
            
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                myLib.sortByYear();
                myLib.showShelf();
            } else if (choice.equals("2")) {
                System.out.print("Enter analysis keyword: ");
                myLib.getConcordance(sc.nextLine(), 12);
            } else if (choice.equals("3")) {
                exit = true;
                System.out.println("Exiting... Goodbye!");
            }
        }
        sc.close();
    }
}