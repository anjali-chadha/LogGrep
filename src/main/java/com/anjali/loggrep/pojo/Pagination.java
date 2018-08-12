package com.anjali.loggrep.pojo;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private List<String> output;
    private int singlePageSize;
    private int pageCount;

    public Pagination(List<String> output, int singlePageSize) {
        this.output = output;
        this.singlePageSize = singlePageSize;
        this.pageCount = (int) Math.ceil(output.size()/singlePageSize) + 1;
    }

    public List<String> getPage(int pageNo) {
        if(pageNo * singlePageSize > output.size()) {
            return null;
        }
        return new ArrayList<>(output.subList(singlePageSize * pageNo, Math.min(singlePageSize * (pageNo + 1), output.size())));
    }

    public int getPageCount() {
        return pageCount;
    }

}
