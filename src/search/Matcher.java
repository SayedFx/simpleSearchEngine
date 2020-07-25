package search;

import java.util.Collection;

public interface Matcher {
    Collection<String> match(String query);
}
