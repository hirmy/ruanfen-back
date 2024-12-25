package com.ruanfen.result;

import com.ruanfen.Docs.PatentDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatentDocResult {
    private List<PatentDoc> patentDocs;
    private int count;
}
