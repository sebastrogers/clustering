package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import clustering.*; 

public class Cluster {
	
	public List points;
	public Point centroid;
	public int id;
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList();
		this.centroid = null;
	}
 
	public List getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
 
	public void setPoints(List points) {
		this.points = points;
	}
 
	public Point getCentroid() {
		return centroid;
	}
 
	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}
 
	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
	
	public void plotCluster() {
		System.out.println("[Cluster: " + id+"]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for (Iterator itr = points.iterator(); itr.hasNext();) {
			Point point = (Point) itr.next();
		
			System.out.println(point);
		}
		System.out.println("]");
	}
 
}