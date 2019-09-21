/**
 * Unfinished class
 */
package makula;

import java.util.ArrayList;

public class CustomFilter {

    private ArrayList<Filter> filters = new ArrayList<>();

    public void saveFilter() {
        // save filters as a serializable form
    }

    public ArrayList<Filter> openFilter() {
        // open filter from file system
        // save to filters
        return filters;
    }

    public void createFilter() {

    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    public void undoAdd() {
        filters.remove(filters.size());
    }

    public void apply(MakImage image) {
        for (Filter filter : filters) {
            filter.setImage(image);
            filter.apply();
        }
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<Filter> filters) {
        this.filters = filters;
    }
}