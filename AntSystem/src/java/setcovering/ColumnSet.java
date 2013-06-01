package setcovering;

import java.util.ArrayList;
import java.util.List;

public class ColumnSet {
	
	private List<Column> columns = new ArrayList<Column>();
	
	public void addColumn(Column column) {
		columns.add(column);
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public Double getCost() {
		Double cost = 0d;
		for (Column column : columns) {
			cost += column.getCost();
		}
		return cost;
	}
}