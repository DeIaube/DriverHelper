package com.example.administrator.olddriverpromotionexam.util;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.bean.Experience;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public final class ExperienceBank {
    private List<Experience> allExperience;


    public void init(Context context, String fileName){
        if(fileName == null || fileName.isEmpty()){
            fileName = "科目二三.xls";
        }
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(context.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheet(0);
        int n = sheet.getRows();

        Experience experience = null;
        for(int i = 1; i < n;i++){
            experience = getExperience(i,sheet);
            allExperience.add(experience);
        }
    }

    private Experience getExperience(int row, Sheet sheet) {
        Experience experience = new Experience();
        experience.setTitle(sheet.getCell(0, row).getContents());
        experience.setContent(sheet.getCell(1, row).getContents());
        return experience;
    }

    public List<Experience> getAllExperience() {
        return allExperience;
    }

    public static ExperienceBank getDefault(){
        return ExperienceBankHolder.experienceBank;
    }

    static class ExperienceBankHolder{
        private static ExperienceBank experienceBank = new ExperienceBank();
    }

    private ExperienceBank() {
        allExperience = new ArrayList<>();
    }
}
