package search;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AllMatcher implements Matcher{
    private final String[] persons;

    @Override
    public Collection<String> match(String query) {
        Set<String> persons = new HashSet<>();
        for (String person : this.persons) {
            boolean matches = true;
            for (String s : query.split("\\s+")) {
                if (!person.toLowerCase().contains(s.toLowerCase())){
                    matches = false;
                    break;
                }
            }
            if (matches) persons.add(person);

        }
        return persons;
    }

    public AllMatcher(String[] persons) {
        this.persons = persons;
    }
}
