
package com.example.android.getuserdatabase.Utils;

import com.example.android.getuserdatabase.Entities.Info;
import com.example.android.getuserdatabase.Entities.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomAPI {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "RandomAPI{" +
                "results=" + results +
                ", info=" + info +
                '}';
    }
}
