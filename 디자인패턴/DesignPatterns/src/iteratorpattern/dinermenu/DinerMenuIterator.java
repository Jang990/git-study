package iteratorpattern.dinermenu;

import iteratorpattern.Iterator;
import iteratorpattern.MenuItem;

public class DinerMenuIterator implements Iterator {
	MenuItem[] items;
	int position = 0;
	
	public DinerMenuIterator(MenuItem[] items) {
		this.items = items;
	}
	
	@Override
	public Object next() {
		MenuItem menuItem = items[position];
		position++;
		return menuItem;
	}
	
	@Override
	public boolean hasNext() {
		if(position >= items.length || items[position] == null)
			return false;
		else
			return true;
	}
}
