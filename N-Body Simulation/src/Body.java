public class Body
{
	private double x;
	private double y;
	private double xv;
	private double yv;
	private double mass;
	private String fileName;
	
	public Body(double x, double y, double xv, double yv, double mass, String fileName)
	{
		this.x = x;
		this.y = y;
		this.xv = xv;
		this.yv = yv;
		this.mass = mass;
		this.fileName = fileName;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getXV()
	{
		return this.xv;
	}
	
	public void setXV(double xv)
	{
		this.xv = xv;
	}
	
	public double getYV()
	{
		return this.yv;
	}
	
	public void setYV(double yv)
	{
		this.yv = yv;
	}
	
	public double getMass()
	{
		return this.mass;
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
}