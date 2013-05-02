package com.dawkinstan.simplebalance;

public class Balance {

	private long id;
	private String transaction_type;
	private String transaction_text;
	private double amountTransacted;
	
	// Id getters/setters
	public void setId(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}
	
	// TransactionType getter/setters
	public void setTransactionType(String transaction_type){
		this.transaction_type = transaction_type;
	}
	public String getTransactionType(){
		return this.transaction_type;
	}
	
	// TransactionText getter/setters
	public void setTransactionText(String transaction_text){
		this.transaction_text = transaction_text;
	}
	public String getTransactionText(){
		return this.transaction_text;
	}
	
	// AmountTransacted getter/setters
	public void setAmountTransacted(double amountTransacted){
		this.amountTransacted = amountTransacted; 
	}
	public double getAmountTransacted(){
		return this.amountTransacted;
	}
}
