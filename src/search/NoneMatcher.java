package search;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NoneMatcher implements Matcher {
    private final String[] persons;

    @Override
    public Collection<String> match(String query) {
        Set<String> persons = new HashSet<>();
        for (String person : this.persons) {
            boolean matchesNot = true;
            for (String s : query.split("\\s+")) {
                if (person.toLowerCase().contains(s.toLowerCase())){
                    matchesNot = false;
                    break;
                }
            }
            if (matchesNot) persons.add(person);

        }
        return persons;
    }

    public NoneMatcher(String[] persons) {
        this.persons = persons;
    }
}
