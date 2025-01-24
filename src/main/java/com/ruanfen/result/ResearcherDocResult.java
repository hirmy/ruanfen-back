package com.ruanfen.result;

import com.ruanfen.Docs.ResearcherDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearcherDocResult {
    private List<ResearcherDoc> researcherDocs;
    private int count;
}
