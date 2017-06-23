package com.ccd.models;

public class Goal {
    private String date;
    private String review;
    private String reviewDate;
    private String goalType;
    private String timeFrame;
    private String goalStatement;
    private String change;
    private String importance;
    private String steps;
    private Code icdCode;

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getReview() {
	return review;
    }

    public void setReview(String review) {
	this.review = review;
    }

    public String getReviewDate() {
	return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
	this.reviewDate = reviewDate;
    }

    public String getGoalType() {
	return goalType;
    }

    public void setGoalType(String goalType) {
	this.goalType = goalType;
    }

    public String getTimeFrame() {
	return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
	this.timeFrame = timeFrame;
    }

    public String getGoalStatement() {
	return goalStatement;
    }

    public void setGoalStatement(String goalStatement) {
	this.goalStatement = goalStatement;
    }

    public String getChange() {
	return change;
    }

    public void setChange(String change) {
	this.change = change;
    }

    public String getImportance() {
	return importance;
    }

    public void setImportance(String importance) {
	this.importance = importance;
    }

    public String getSteps() {
	return steps;
    }

    public void setSteps(String steps) {
	this.steps = steps;
    }

   

    /**
     * @return the icdCode
     */
    public Code getIcdCode() {
        return icdCode;
    }

    /**
     * @param icdCode the icdCode to set
     */
    public void setIcdCode(Code icdCode) {
        this.icdCode = icdCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	return "Goal [date=" + date + ", review=" + review + ", reviewDate=" + reviewDate + ", goalType=" + goalType
		+ ", timeFrame=" + timeFrame + ", goalStatement=" + goalStatement + ", change=" + change
		+ ", importance=" + importance + ", steps=" + steps + ", icdCode=" + icdCode + "]";
    }

}
