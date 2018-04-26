package com.scujcc.android.memorandum;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by George on 2017/12/2.
 */

public class MemorandumLab {
    private static MemorandumLab sMemorandumLab;

    private List<Memorandum> mMemorandums;

    public static MemorandumLab get(Context context){
        if (sMemorandumLab == null){
            sMemorandumLab = new MemorandumLab(context);
        }
        return sMemorandumLab;
    }

    private MemorandumLab(Context context){
        mMemorandums = new ArrayList<>();
    }

    public void addMemorandum(Memorandum c){
        mMemorandums.add(c);
    }

    public List<Memorandum> getMemorandums(){
        return mMemorandums;
    }

    public Memorandum getMemorandum(UUID id){
        for (Memorandum memorandum : mMemorandums){
            if (memorandum.getID().equals(id)){
                return memorandum;
            }
        }
        return null;
    }
}
