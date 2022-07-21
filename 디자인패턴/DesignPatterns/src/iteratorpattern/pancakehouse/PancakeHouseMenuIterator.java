package iteratorpattern.pancakehouse;

import java.util.ArrayList;

import iteratorpattern.Iterator;
import iteratorpattern.MenuItem;

public class PancakeHouseMenuIterator implements Iterator {
	ArrayList<MenuItem> items;
	int position = 0;
	
	public PancakeHouseMenuIterator(ArrayList<MenuItem> items) {
		this.items = items;
	}
	
	@Override
	public Object next() {
		MenuItem menuItem = items.get(position);
		position++;
		return menuItem;
	}
	
	@Override
	public boolean hasNext() {
		if(position >= items.size() || items.get(position) == null)
			return false;
		else
			return true;
	}
}
