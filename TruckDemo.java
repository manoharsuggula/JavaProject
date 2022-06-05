package demo19025;

import base.Truck;
import base.Hub;
import base.Highway;
import base.Location;
import java.lang.Math;

class TruckDemo extends Truck {
	public TruckDemo()	//Constructor.
	{
		time = 0;	//Time member which stores the timesteps.
		dest = false;	//dest boolean which stores whether it has reached the destination or not.
		onhwy = false;	//onhwy boolean which stores whether it is on highway or not.
	}

	@Override
	protected synchronized void update(int deltaT)	//A method which updates the position and state of a truck. 
	{
		time += deltaT;	//Adding the step.
		if(time > this.getStartTime() && !dest)	//Checking whether the time is greater than start time and it has reached the destination.
		{
			Hub dest_hub = NetworkDemo.getNearestHub(this.getDest());	//Finding the hub nearest to destination.
			if((this.getLoc().getX() == this.getSource().getX()) && (this.getLoc().getY() == this.getSource().getY()))	//Checking whether the hub is at the source or not
			{
				Hub h = NetworkDemo.getNearestHub(this.getSource());	//Going to the nearest hub.
				boolean check = h.add(this);	//Adding it to the hub.
				if(check)
				{
					this.setLoc(h.getLoc());
					this.onhwy = false;
				}
			}
			else if(onhwy)	//Checking whether it is on highway or not.
			{
				int dist = current_highway.getMaxSpeed()*deltaT/1000;	//Finding the distance it will travel. 
				distance += dist;	//Adding it to total distance travelled in this highway.
				int d = (int)Math.sqrt(current_highway.getEnd().getLoc().distSqrd(current_highway.getStart().getLoc()));	//Finding the length of the highway.
				if(distance >= d)	//Checking whether the total distance travelled is greater than the length.
				{
					//Checking whether the hub reached is the hub nearer to the destination.
					if(current_highway.getEnd().getLoc().getX() != dest_hub.getLoc().getX() && current_highway.getEnd().getLoc().getY() != dest_hub.getLoc().getY())
					{
						//If not adding it to the hub.
						if(current_highway.getEnd().add(this))
						{
							onhwy = false;
							this.setLoc(current_highway.getEnd().getLoc());
							current_highway.remove(this);
						}
					}
					//If it is the destination, sending it to destination Location.
					else
					{
						this.setLoc(this.getDest());
						dest = true;
					}
				}
				else	//Updating the position of the truck on the highway.
				{
					int x1, x2, y1,y2;
					x1 = current_highway.getStart().getLoc().getX();
					y1 = current_highway.getStart().getLoc().getY();
					x2 = current_highway.getEnd().getLoc().getX();
					y2 = current_highway.getEnd().getLoc().getY();
					double cos_x = (x2-x1)/Math.sqrt(current_highway.getEnd().getLoc().distSqrd(current_highway.getStart().getLoc()));
					double sin_x = (y2-y1)/Math.sqrt(current_highway.getEnd().getLoc().distSqrd(current_highway.getStart().getLoc()));	
					int speed = current_highway.getMaxSpeed();			
					int x = this.getLoc().getX(),y = this.getLoc().getY();
					x += Math.round(speed*deltaT*cos_x/1000);	//Finding the updated x value.
					y += Math.round(speed*deltaT*sin_x/1000);	//Finding the updated y value.
					
					this.setLoc(new Location(x,y)) ;
				}
			}
		}
	}
	
	public synchronized Hub getLastHub()	//Getting the last hub, which it left.
	{
		return current_highway.getStart();
	}

	public synchronized void enter(Highway hwy)	//Sending the truck to a highway.
	{
		current_highway = hwy;
		onhwy = true;
		distance = 0;
	}

	public synchronized String getTruckName()	//Returning the truck name.
	{
		return "Truck19025";
	}



	private Highway current_highway;
	private boolean onhwy;
	private int time;
	private int distance;
	private boolean dest;
}
