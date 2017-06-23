package com.ccd.parser;

import com.ccd.models.CarePlan;

public class Main {
    static CarePlan carePlan;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	System.out.println(new CCDParser().parse("D:/XML Documents/Green And Seidner/Phyllis_Anastasi_100429.XML"));
    }

}
