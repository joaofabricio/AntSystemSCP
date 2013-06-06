package antsystem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import setcovering.Column;
import setcovering.ColumnSet;
import setcovering.Line;

public class Ant {
	
	private ColumnSet bestSolution;
	private ColumnSet totalColumns;
	
	public Ant(ColumnSet totalColumns) {
		this.totalColumns = totalColumns;
		bestSolution = new ColumnSet(totalColumns.getTotalLines());
	}

	public ColumnSet run(Double alfa, Double beta, Double ro, Double q) {
		
		while (!coverAllLines(bestSolution)) {
			Column column = chooseColumn(alfa, beta);
			System.out.println("coluna escolhida: "+column);
			
			bestSolution.addColumn(column);
			
			System.out.println("solution: "+bestSolution);
			
		}
		
		updatePheromone(ro, q);
		
		return bestSolution;
	}

	private void updatePheromone(Double ro, Double q) {
		for (Column column : bestSolution.getColumns()) {
			column.incPheromone(q, bestSolution.getCost(), ro);
		}
		
	}

	private boolean coverAllLines(ColumnSet bestSolution) {
		Set<Line> set = new HashSet<>();
		for (Column column : bestSolution.getColumns()) {
			set.addAll(column.getLines());
		}
		
		return set.size() == totalColumns.getTotalLines()-1;
	}

	private Column chooseColumn(Double alfa, Double beta) {
		//pj =(tj^a*nj^b)/#tj^a*nj^b
		//nj=visibilidade
		
		calculateProb(alfa, beta);
		
		
		double randomNumber = Math.random();
		
		Column choosed = null; 
		double ac = 0;
		Iterator<Column> columns = totalColumns.getColumns().iterator();
		while (ac < randomNumber && columns.hasNext()) {
			choosed = columns.next();
			ac += choosed.getProb();
		}
		return choosed;
	}

	private void calculateProb(Double alfa, Double beta) {
		double sumProbs = 0;
		for (Column column : totalColumns.getColumns()) {
			sumProbs += Math.pow(column.getPheromone(), alfa) +
					Math.pow(column.getVisibility(), beta);
		}
		
		for (Column column : totalColumns.getColumns()) {
			double probCol = Math.pow(column.getPheromone(), alfa) +
						  Math.pow(column.getVisibility(), beta);
			column.setProb(probCol / sumProbs);
		}
	}
	
}
