package com.report.generator.Item;

import com.report.generator.entity.InputEntity;
import com.report.generator.entity.OutputEntity;
import com.report.generator.entity.ReferenceEntity;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvItemProcessor implements ItemProcessor<InputEntity, OutputEntity> {
    private final Map<String, ReferenceEntity> referenceData = new HashMap<>();

    public CsvItemProcessor(List<ReferenceEntity> referenceEntities) {
        for (ReferenceEntity ref : referenceEntities) {
            String key = ref.getRefkey1() + "_" + ref.getRefkey2();
            referenceData.put(key, ref);
        }
    }

    @Override
    public OutputEntity process(InputEntity item) throws Exception {
        String key = item.getRefkey1() + "_" + item.getRefkey2();
        ReferenceEntity ref = referenceData.get(key);

        if (ref == null) {
            throw new IllegalStateException("No matching reference data found for key: " + key);
        }

        OutputEntity output = new OutputEntity();
        output.setOutfield1(item.getField1() + item.getField2());
        output.setOutfield2(ref.getRefdata1());
        output.setOutfield3(ref.getRefdata2() + ref.getRefdata3());
        BigDecimal maxField5 = item.getField5().max(ref.getRefdata4());
        output.setOutfield4(new BigDecimal(item.getField3()).multiply(maxField5));
        output.setOutfield5(maxField5);

        return output;
    }
}
