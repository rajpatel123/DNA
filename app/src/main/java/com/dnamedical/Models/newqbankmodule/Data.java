package com.dnamedical.Models.newqbankmodule;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("modules_mcqs")
@Expose
private List<ModulesMcq> modulesMcqs = null;

public List<ModulesMcq> getModulesMcqs() {
return modulesMcqs;
}

public void setModulesMcqs(List<ModulesMcq> modulesMcqs) {
this.modulesMcqs = modulesMcqs;
}

}

