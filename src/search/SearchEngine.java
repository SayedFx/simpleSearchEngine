package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SearchEngine {
    private static final int FIND_A_PERSON = 1;
    private static final int PRINT_ALL = 2;
    private static final int EXIT = 0;

    private Map<String, Set<Integer>> invertedIndex;
    private MatchingEngine engine;
    private String[] people;

    public SearchEngine(String[] args) throws IOException {
        if (args != null && args.length == 2 && args[0].equals("--data")) {
            people = Files.readString(Path.of(args[1])).split("\\n");
            invertedIndex = new HashMap<>();
            fillIndex();
            engine = new MatchingEngine();

        }
    }

    public void start() {
        printMenu();
    }

    private static void printSearchMenu() {
        System.out.print("\n\n=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit\n");
    }

    private String getQuery() {
        return new Scanner(System.in).nextLine().trim().toLowerCase();
    }

    private void printQueryMenu() {
        System.out.println("Enter a name or email to search all suitable people.\n");
    }

    private String getStrategyChoice() {
        return new Scanner(System.in).nextLine().trim();
    }

    private void printStrategyMenu() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE\n");
    }

    private void printMenu() {
        printSearchMenu();
        int choice = getSearchMenuChoice();
        switch (choice) {
            case FIND_A_PERSON -> {
                findPerson();
                printMenu();
            }
            case PRINT_ALL -> {
                System.out.println("=== List of people ===");
                for (String person : people) {
                    System.out.println(person);
                }
                printMenu();
            }
            case EXIT -> System.out.println("Bye!");
            default -> System.out.println("Incorrect option! Try again.");
        }
    }

    private void fillIndex() {
        for (int i = 0; i < people.length; i++) {
            for (String s : people[i].split(" ")) {
                String key = s.trim().toLowerCase();
                Set<Integer> indexes = invertedIndex.getOrDefault(key, new HashSet<>());
                indexes.add(i);
                invertedIndex.put(key, indexes);
            }
        }
    }

    private int getSearchMenuChoice() {
        return new Scanner(System.in).nextInt();
    }

    private void findPerson() {
        printStrategyMenu();
        String strategy = getStrategyChoice();
        printQueryMenu();
        String query = getQuery();
        switch (strategy) {
            case "ANY":
                processQuery(query, new AnyMatcher(invertedIndex, people));
                break;
            case "ALL":
                processQuery(query, new AllMatcher(people));
                break;
            case "NONE":
                processQuery(query, new NoneMatcher(people));
                break;
            default:
        }
    }

    private void processQuery(String query, Matcher matcher) {
        engine.setMatcher(matcher);
        Collection<String> match = engine.match(query);
        if (!match.isEmpty()) {
            System.out.printf("%d persons found:\n", match.size());
            match.forEach(System.out::println);
        } else System.out.println("No matching people found.");
    }
}
