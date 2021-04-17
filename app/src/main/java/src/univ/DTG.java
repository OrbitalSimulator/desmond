package src.univ;

/**
 * Group 22
 * A class to hold a Date Time Group in the format of YYYY-MM-DD-HH-MM
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class DTG 
{
	public int yr;
	public int mth;
	public int day;
	public int hr;
	public int min;
	public int sec;
	private String[] month = {"zero","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov"};
	
	public DTG()
	{
		this.yr = 0; 
		this.mth = 0;
		this.day = 0;
		this.hr = 0; 
		this.min = 0;
		this.sec = 0;
	}
	
	public DTG(String string)
	{
		// TODO (Leon) Add DTG from string Implementation
		this.yr = 0; 
		this.mth = 0;
		this.day = 0;
		this.hr = 0; 
		this.min = 0;
		this.sec = 0;
	}
	
	public DTG(int yr, int mth, int day, int hr, int min, int sec)       
	{                  
		this.yr = yr;   
		this.mth = mth;  
		this.day = day;  
		this.hr = hr;   
		this.min = min;  
		this.sec = sec;  
	}
	
	public DTG(int yr, String s, int day, int hr, int min, int sec)       
	{                  
		int mth = 0; 
		for(int i = 1; i < 12; i++)
		{
			if(s.equals(month[i]))
			{
				mth = i;
				break;
			}
		}		
		this.yr = yr;   
		this.mth = mth;  
		this.day = day;  
		this.hr = hr;   
		this.min = min;  
		this.sec = sec;  
	}
	
	public void set(int yr, int mth, int day, int hr, int min, int sec)       
	{                  
		this.yr = yr;   
		this.mth = mth;  
		this.day = day;  
		this.hr = hr;   
		this.min = min;  
		this.sec = sec;  
	}

	
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(yr + "-" + month[mth] + "-" + day + "_" + hr + ":" + min + ":" + sec);
		return s.toString();
	}
	
	public String toStringNoSec()
	{
		StringBuilder s = new StringBuilder();
		s.append(yr + "-" + month[mth] + "-" + day + "_" + hr + ":" + min);
		return s.toString();
	}
	
	public String toStringNoMin()
	{
		StringBuilder s = new StringBuilder();
		s.append(yr + "-" + month[mth] + "-" + day + "_" + hr);
		return s.toString();
	}
	
	public String toStringDate()
	{
		StringBuilder s = new StringBuilder();
		s.append(yr + "-" + month[mth] + "-" + day);
		return s.toString();
	}

	//Method called from currentTime stamp until to target timestamp, to ensure its less 
	//If target is in the future return true
	//If times are equal return false
	public boolean timeComparison(DTG target)
	{
		//Year comparison
		if(this.yr < target.yr)
		{
			return true;
		}
		else if (this.yr == target.yr)
		{
			//Month comparison
			if(this.mth < target.mth)
			{
				return true;
			}
			else if (this.mth == target.mth)
			{
				//Day comparison
				if(this.day < target.day)
				{
					return true;
				}
				else if (this.day == target.day)
				{
					//Hour comparison
					if(this.hr < target.hr)
					{
						return true;
					}
					else if (this.hr == target.hr)
					{
						//Minute comparison
						if(this.min < target.min)
						{
							return true;
						}
						else if (this.min == target.min)
						{
							//Minute comparison
							if(this.sec < target.sec)
							{
								return true;
							}
							else if (this.sec == target.sec)
							{
								if(DEBUG)
								{
									System.out.println("Times are equal");
								}
								return false;
							}
							else
							{
								return false;
							}
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public void addSeconds(double sec) {
		int seconds = (int) sec;
		int minutes = 0;
		int hours = 0;
		int days = 0;
		int months = 0;
		int years = 0;
		if(seconds < 60) {
			this.sec += seconds;
		}else {
			minutes = seconds/60;
			seconds = seconds%60;
			if(minutes > 59) {
				hours = minutes/60;
				minutes = minutes%60;
				if(hours > 23) {
					days = hours/24;
					hours = hours%24;
					if(this.mth == 2) {
						if(days > 28) {
							months = days/29;
							days = days%29;
							if(months > 12) {
								years =  months/13;
								months = months%13;
							}
						}
					}else if(this.mth == 4 || this.mth == 6 || this.mth == 9 || this.mth == 11) {
						if(days > 30) {
							months = days/31;
							days = days%31;
							if(months > 12) {
								years = months/13;
								months = months%13;
							}
						}
					}else {
						if(days>31) {
							months = days/32;
							days = days%32;
							if(months > 12) {
								years =  months/13;
								months = months%13;
							}
						}
					}
				}
			}
			DTG toAdd =  new DTG(years, months, days, hours, minutes, seconds);
			this.add(toAdd);
		}
	}
	
	public void add(DTG dtg) {
		boolean zero = false;
		this.sec+=dtg.sec;
		this.min+=this.sec/60;
		this.sec=this.sec%60;
		this.min+=dtg.min;
		this.hr+=this.min/60;
		this.min=this.min%60;
		this.hr+=dtg.hr;
		this.day+=this.hr/24;
		this.hr=this.hr%24;
		this.day+=dtg.day;
		this.yr+=dtg.yr;
		if(this.day == 0 && dtg.day == 0){
			zero = true;
		}
		if(this.mth==2) {
			this.mth+=this.day/29;
			this.day=this.day%29;
			this.mth+=dtg.mth;
			
			if(this.mth>12) {
				this.yr++;
				this.mth=this.mth%13;
			}
		}else if(this.mth==4 || this.mth==6 || this.mth==9 || this.mth==11) {
			this.mth+=this.day/31;
			this.day=this.day%31;
			this.mth+=dtg.mth;
			
			if(this.mth>12) {
				this.yr++;
				this.mth=this.mth%12;
			}
			
		}else {
			this.mth+=this.day/32;
			this.day=this.day%32;
			this.mth+=dtg.mth;
			
			if(this.mth>12) {
				this.yr++;
				this.mth=this.mth%12;
			}
		}
		if(this.day==0 && !zero) {
			this.day=1;
		}
	}
	
	public DTG sub(DTG dtg) {
		DTG dif =  new DTG();
		if(this.sec >= dtg.sec) {
			dif.sec = this.sec - dtg.sec;
		}else {
			dif.sec = this.sec - dtg.sec;
			dtg.min += 1 + -(dif.sec)/60;
			dif.sec = 60 + (dif.sec%60);
		}
		if(this.min >= dtg.min) {
			dif.min = this.min - dtg.min;
		}else {
			dif.min = this.min - dtg.min;
			dtg.hr += 1 + -(dif.min)/60;
			dif.min = 60 + (dif.min%60);
		}
		if(this.hr >= dtg.hr) {
			dif.hr = this.hr - dtg.hr;
		}else {
			dif.hr = this.hr - dtg.hr;
			dtg.day += 1 + -(dif.hr)/24;
			dif.hr = 24 + (dif.hr%24);
		}
		if(this.mth >= dtg.mth) {
			dif.mth = this.mth - dtg.mth;
		}else{
			dif.mth = this.mth - dtg.mth;
			dtg.yr += 1 + -(dif.mth)/12;
			dif.mth = 12 + (dif.mth%12);
		}
		if(this.day >= dtg.day) {
			dif.day = this.day - dtg.day;
		}else {
			dif.day = this.day - dtg.day;
			if((dif.mth-1)==2) {
				dif.mth -= 1 + -(dif.day)/28;
				dif.day = 28 + (dif.day%31);
			}else if(dif.mth-1==4 || dif.mth-1==6 || dif.mth-1==9 || dif.mth-1==11) {
				dif.mth -= 1 + -(dif.day)/30;
				dif.day = 30 + (dif.day%30);
			}else {
				dif.mth -= 1 + -(dif.day)/31;
				dif.day = 31 + (dif.day%31);
			}
		}
		if(this.yr >= dtg.yr) {
			dif.yr = this.yr-dtg.yr;
		}else {
			dif.yr = 0;
		}
		return dif;
	}

	private static final boolean DEBUG = false;
}
