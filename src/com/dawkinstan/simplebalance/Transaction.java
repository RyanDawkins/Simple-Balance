package com.dawkinstan.simplebalance;

public class Transaction {
	
	public static int TYPE_GAS = 0;
	public static int TYPE_FOOD = 1;
	public static int TYPE_ENTERTAINMENT = 2;
	public static int TYPE_BILLS = 3;
	public static int TYPE_SIGNIFICANT_OTHER = 4;

	private double beforePurchase;
	private double afterPurchase;
	private double amountSpent;
	private String whereSpent;
	private int type;
	private String title;
	private boolean isDeposit;
	private long id;
	
	public Transaction(double beforePurchase, double afterPurchase, double amountSpent)
	{
		this.beforePurchase = beforePurchase;
		this.afterPurchase = afterPurchase;
		this.amountSpent = amountSpent;
		
		if(beforePurchase > afterPurchase)
		{
			this.isDeposit = true;
		}
		else
		{
			this.isDeposit = false;
		}
		
	}
	
	// Title methods
	public void setTitle(String title){ this.title = title; }
	public String getTitle(){ return this.title; }
	
	// Type methods
	public void setType(int type){ this.type = type; }
	public int getType(){ return this.type; }
	
	// Where spent methods
	public void setWhereSpent(String whereSpent){ this.whereSpent = whereSpent; }
	public String getWhereSpent(){ return this.whereSpent; }
	
	// Monetary methods
	public double getAfterPurchase(){ return this.afterPurchase; }
	public double getBeforePurchase(){ return this.beforePurchase; }
	public double getAmountSpent(){ return this.amountSpent; }
	public boolean isDeposit(){ return this.isDeposit; }
	
	public void setId(long id){ this.id = id; }
	public long getId(){ return this.id; };
	
	public String toString()
	{
		String item = "";
		item = this.title + " at " + this.whereSpent;
		item = this.isDeposit() ? item + " +" + this.amountSpent : item + " -" + this.amountSpent;
		return item;
	}
	
}
