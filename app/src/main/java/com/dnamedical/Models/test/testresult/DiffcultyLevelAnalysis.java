package com.dnamedical.Models.test.testresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiffcultyLevelAnalysis {

@SerializedName("easy")
@Expose
private Easy easy;
@SerializedName("medium")
@Expose
private Medium medium;
@SerializedName("hard")
@Expose
private Hard hard;

public Easy getEasy() {
return easy;
}

public void setEasy(Easy easy) {
this.easy = easy;
}

public Medium getMedium() {
return medium;
}

public void setMedium(Medium medium) {
this.medium = medium;
}

public Hard getHard() {
return hard;
}

public void setHard(Hard hard) {
this.hard = hard;
}

}