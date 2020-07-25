package search;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnyMatcher implements Matcher{
    private final Map<String, Set<Integer>> reverseIndex;
    private final String[] persons;

    @Override
    public Collection<String> match(String query) {
        Set<String> persons = new HashSet<>();
        for (String s : query.split("\\s+")) {
            Collection<Integer> indexes = reverseIndex.get(s);
            if (indexes!=null&&!indexes.isEmpty()){
                indexes.forEach(i -> persons.add(this.persons[i]));
            }
        }
        return persons;
    }

    public AnyMatcher(Map<String, Set<Integer>> reverseIndex , String[] persons) {
        this.reverseIndex = reverseIndex;
        this.persons = persons;
    }
}
