package com.dnamedical.Models.subs.points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanPoints {

@SerializedName("A")
@Expose
private A a;
@SerializedName("B")
@Expose
private B b;
@SerializedName("C")
@Expose
private C c;
@SerializedName("D")
@Expose
private D d;
@SerializedName("firstYear")
@Expose
private List<FirstYear> firstYear = null;
@SerializedName("secondyear")
@Expose
private List<Secondyear> secondyear = null;
@SerializedName("prefinal")
@Expose
private List<Prefinal> prefinal = null;
@SerializedName("final")
@Expose
private List<Final> _final = null;
@SerializedName("shortSubject")
@Expose
private List<ShortSubject> shortSubject = null;

public A getA() {
return a;
}

public void setA(A a) {
this.a = a;
}

public B getB() {
return b;
}

public void setB(B b) {
this.b = b;
}

public C getC() {
return c;
}

public void setC(C c) {
this.c = c;
}

public D getD() {
return d;
}

public void setD(D d) {
this.d = d;
}

public List<FirstYear> getFirstYear() {
return firstYear;
}

public void setFirstYear(List<FirstYear> firstYear) {
this.firstYear = firstYear;
}

public List<Secondyear> getSecondyear() {
return secondyear;
}

public void setSecondyear(List<Secondyear> secondyear) {
this.secondyear = secondyear;
}

public List<Prefinal> getPrefinal() {
return prefinal;
}

public void setPrefinal(List<Prefinal> prefinal) {
this.prefinal = prefinal;
}

public List<Final> getFinal() {
return _final;
}

public void setFinal(List<Final> _final) {
this._final = _final;
}

public List<ShortSubject> getShortSubject() {
return shortSubject;
}

public void setShortSubject(List<ShortSubject> shortSubject) {
this.shortSubject = shortSubject;
}

}