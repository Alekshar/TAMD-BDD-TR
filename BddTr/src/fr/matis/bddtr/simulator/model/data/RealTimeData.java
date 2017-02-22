package fr.matis.bddtr.simulator.model.data;

public class RealTimeData extends AbstractData {
	private long stamp;
	private int duration;
	private int updatePeriod;
	private static final double UPDATE_PERIOD = 2./3.;

	public RealTimeData(int duration) {
		this(0, duration);
	}

	public RealTimeData(long stamp, int duration) {
		super();
		this.stamp = stamp;
		this.duration = duration;
		this.updatePeriod = (int) (duration*UPDATE_PERIOD);
		if(this.updatePeriod == 0){
			this.updatePeriod = 1;
		}
	}
	
	public long getStamp() {
		return stamp;
	}
	public void setStamp(long stamp) {
		this.stamp = stamp;
	}
	public int getDuration() {
		return duration;
	}
	public long getNextUpdateStamp() {
		return (long) (stamp+updatePeriod) ;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RealTimeData [stamp=");
		builder.append(stamp);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append("]");
		return builder.toString();
	}


	
}
