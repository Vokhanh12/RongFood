package com.example.test.Model.Model_Dashbroad.useSearchView;

import androidx.annotation.NonNull;
import com.example.test.Model.VietnameseDelicacies;
import org.jetbrains.annotations.NotNull;

public class MenuShow_VietnameseDelicacies {

    private VietnameseDelicacies vietnameseDelicacies;
    private String _MonAn;


    public MenuShow_VietnameseDelicacies(String MonAn) {
        this._MonAn = MonAn;

    }




    public String get_MonAn() {
        return _MonAn;
    }

    public void set_MonAn(String _MonAn) {
        this._MonAn = _MonAn;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return this._MonAn;
    }
}
