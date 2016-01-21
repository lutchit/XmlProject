package com.ujm.xmltech.entity;

public enum CodeErreurEnum {
	
	// No problem
	CORRECT,
	
	/* Banque differente des autres groupes */
	RJC000,
	
	/* Montant < 1E */
	RJC001,
	
	/* Montant > 10000E */
	RJC002,
	
	/* Montant != Euro */
	RJC003,
	
	/* Transaction dans le passé */
	RJC004,
	
	/* Transaction futuriste */
	RJC005,
	
	/* RCUR et date < 2 jour */
	RJC006,
	
	/* FRST et date < 5 jour */
	RJC007,
	
	/* RCUR et pas de mandat */
	RJC008;

}