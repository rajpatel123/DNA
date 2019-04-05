package com.dnamedical.Adapters;

public class result {



    private String name;
    private String rank;
    private String question;

    public result(String name, String rank, String question) {
        this.name = name;
        this.rank = rank;
        this.question = question;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



}