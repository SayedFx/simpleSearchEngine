package search;

import java.util.Collection;

public class MatchingEngine {
    private Matcher matcher;

    public void setMatcher(Matcher matcher){
        this.matcher = matcher;
    }

    public Collection<String> match(String query){
        return matcher.match(query);
    }
}
