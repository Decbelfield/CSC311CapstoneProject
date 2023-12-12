package com.example.csc311capstoneproject;

public class FormW2 {
    private String boxB, boxC, boxE, box1, box2, box3, box4, box5, box6, box15, box16, box17, box18, box19;
    private Client client;
    private double taxRate;

    public FormW2() {
        boxB = null;boxC = null;boxE = null;
        box1 = null;box2 = null;box3 = null;
        box4 = null;box5 = null;box6 = null;
        box15= null;box16= null;box17 = null;
        box18= null;box19 = null;

        client = null;
    }

    public FormW2(Client c) {
        client = c;

        boxB = null;boxC = null;
        box1 = null;box2 = null;box3 = null;
        box4 = null;box5 = null;box6 = null;
        box15= null;box16= null;box17 = null;
        box18= null;box19 = null;

        String e = c.getFirstName() + "     " + c.getMiddleInitial() + "    " + c.getLastName() + "\n"
                   + c.getAddress() + "     " + c.getZip();

        boxE = e;

    }

    public void setAll(String boxB, String boxC,String boxE,String box1,String box2,String box3,String box4,String box5,String box6,String box15,String box16,String box17,String box18,String box19) {

        this.boxB = boxB;this.boxC = boxC;this.boxE = boxE;
        this.box1 = box1;this.box2 = box2;this.box3 = box3;
        this.box4 = box4;this.box5 = box5;this.box6 = box6;
        this.box15= box15;this.box16= box16;this.box17 = box17;
        this.box18= box18;this.box19 = box19;

    }

    public String getBoxB() {
        return boxB;
    }

    public void setBoxB(String boxB) {
        this.boxB = boxB;
    }

    public String getBoxC() {
        return boxC;
    }

    public void setBoxC(String boxC) {
        this.boxC = boxC;
    }

    public String getBoxE() {
        return boxE;
    }

    public void setBoxE(String boxE) {
        this.boxE = boxE;
    }

    public String getBox1() {
        return box1;
    }

    public void setBox1(String box1) {
        this.box1 = box1;
    }

    public String getBox2() {
        return box2;
    }

    public void setBox2(String box2) {
        this.box2 = box2;
    }

    public String getBox3() {
        return box3;
    }

    public void setBox3(String box3) {
        this.box3 = box3;
    }

    public String getBox4() {
        return box4;
    }

    public void setBox4(String box4) {
        this.box4 = box4;
    }

    public String getBox5() {
        return box5;
    }

    public void setBox5(String box5) {
        this.box5 = box5;
    }

    public String getBox6() {
        return box6;
    }

    public void setBox6(String box6) {
        this.box6 = box6;
    }

    public String getBox15() {
        return box15;
    }

    public void setBox15(String box15) {
        this.box15 = box15;
    }

    public String getBox16() {
        return box16;
    }

    public void setBox16(String box16) {
        this.box16 = box16;
    }

    public String getBox17() {
        return box17;
    }

    public void setBox17(String box17) {
        this.box17 = box17;
    }

    public String getBox18() {
        return box18;
    }

    public void setBox18(String box18) {
        this.box18 = box18;
    }

    public String getBox19() {
        return box19;
    }

    public void setBox19(String box19) {
        this.box19 = box19;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void calculateWithholdings(){
        double wages = Double.parseDouble(box1);
        double federalWithheld = wages * .0756;
        double socialsecurityWithheld = wages * .062;
        double medicareWithheld = wages * .0145;

        box2 = Double.toString(federalWithheld);
        box4 = Double.toString(socialsecurityWithheld);
        box6 = Double.toString(medicareWithheld);

    }

    public void calculateIncomeTax(){
        double wages = Double.parseDouble(box1);
        calculateTaxRate(wages);
        double totalIncomeTax = wages * taxRate;
        double stateIncomeTax = totalIncomeTax * .45;
        double localIncomeTax = totalIncomeTax * .55;

        box17 = Double.toString(stateIncomeTax);
        box19 = Double.toString(localIncomeTax);

    }

    private void calculateTaxRate(Double income) {
        if((income >= 0) && (income <= 8500)) {
            taxRate = .04;
        }

        else if((income > 8500) && (income <= 11700)) {
            taxRate = .045;
        }

        else if((income > 11700) && (income <= 13900)) {
            taxRate = .0525;
        }

        else if((income > 13900) && (income <= 80650)) {
            taxRate = .055;
        }

        else if((income > 80650) && (income <= 215400)) {
            taxRate = .06;
        }

        else if((income > 215400) && (income <= 1077550)) {
            taxRate = .0685;
        }

        else if((income > 1077550) && (income <= 5000000)) {
            taxRate = .0965;
        }

        else if((income > 5000000) && (income <= 25000000)) {
            taxRate = .103;
        }

        else if (income > 25000000){
            taxRate = .109;
        }

        else {
            taxRate = 0.0;
        }
    }

}
