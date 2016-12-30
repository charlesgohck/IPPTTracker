package ippttracker.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author charlesgoh
 */
@XmlRootElement(name = "results")
public class ResultListWrapper {
    private List <Result> results;
    
    @XmlElement(name = "result")
    public List <Result> getResults() {
        return results;
    }
    
    public void setResults(List <Result> results) {
        this.results = results;
    }
            
}
