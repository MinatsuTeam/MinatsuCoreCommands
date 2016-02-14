package us.tryy3.minatsu.plugins.minatsucorecommands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tryy3 on 2016-02-14.
 */
public class Paginator {
    List<Page> pages;
    int size;

    public Paginator(int size) {
        pages = new ArrayList<Page>();
        this.size = size;
        pages.add(new Page());
    }

    public void addPage(String s) {
        Page page = pages.get(pages.size()-1);
        if (page.size() == this.size) page = new Page();

        page.addText(s);
    }

    public int size() {
        return this.pages.size();
    }

    public Page getPage(int page) {
        return this.pages.get(page);
    }

    public class Page {
        List<String> text;

        public Page() {
           text = new ArrayList<String>();
        }

        public void addText(String text) {
            this.text.add(text);
        }

        public List<String> getText() {
            return text;
        }

        public int size() {
            return this.text.size();
        }
    }
}
