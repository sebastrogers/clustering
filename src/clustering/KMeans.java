package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import clustering.*;

public class KMeans {
 
	//Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS = 4;    
    //Number of Points
    private int NUM_POINTS = 15;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 10;
    
    private List points;
    private List clusters;
    
    public KMeans() {
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();    	
    }
    
    public static void main(String[] args) {
    	
    	KMeans kmeans = new KMeans();
    	kmeans.init();
    	kmeans.calculate();
    }
    
    //Initializes the process
    public void init() {
    	//Create Points
    	points = Point.createRandomPoints(MIN_COORDINATE,MAX_COORDINATE,NUM_POINTS);
    	
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	//Print Initial state
    	plotClusters();
    }
 
	private void plotClusters() {
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster c = (Cluster) clusters.get(i);
    		c.plotCluster();
    	}
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
        	//Clear cluster state
        	clearClusters();
        	
        	List lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		Point p1 = (Point)lastCentroids.get(i);
        		Point p2 = (Point) currentCentroids.get(i);
        		distance += Point.distance(p1,p2);
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	plotClusters();
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
    }
    
    private void clearClusters() {
    	for (Iterator iter = clusters.iterator(); iter.hasNext();) {
			Cluster cluster = (Cluster) iter.next();    	
    		cluster.clear();
    	}
    }
    
    private List getCentroids() {
    	List centroids = new ArrayList(NUM_CLUSTERS);
    	
    	for (Iterator itt = clusters.iterator(); itt.hasNext();) {
			Cluster cluster = (Cluster) itt.next();	
    		Point aux = cluster.getCentroid();
    		Point point = new Point(aux.getX(),aux.getY());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
                
        for (Iterator it = points.iterator(); it.hasNext();) {
			Point point = (Point) it.next();
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	Cluster c = (Cluster) clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            ((Cluster) clusters.get(cluster)).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
    	
    	for (Iterator iterator = clusters.iterator(); iterator.hasNext();) {
			Cluster cluster = (Cluster) iterator.next();
			
            double sumX = 0;
            double sumY = 0;
            List list = cluster.getPoints();
            
            int n_points = list.size();
            
            for (Iterator itr = list.iterator(); itr.hasNext();) {
				Point point = (Point) itr.next();
				
				sumX += point.getX();
                sumY += point.getY();
			}
            
            Point centroid = cluster.getCentroid();
            if(n_points > 0) {
            	double newX = sumX / n_points;
            	double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
    
}