package ac.kr.tukorea.capstone.config.util;

import ac.kr.tukorea.capstone.product.entity.QDetail;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProductFilterDetail {
    COMPANY_EQ_SAMSUNG("1001", ProductFilter.COMPANY_EQ.getQuery("삼성전자", null)),
    COMPANY_EQ_APPLE  ("1002", ProductFilter.COMPANY_EQ.getQuery("APPLE", null)),
    COMPANY_EQ_ETC    ("1003", ProductFilter.COMPANY_ETC.getQuery(null, null)),

    PRICE_BETWEEN_0K_AND_200K    ("1004", ProductFilter.PRICE_BETWEEN.getQuery("0", "200000")),
    PRICE_BETWEEN_200K_AND_400K  ("1005", ProductFilter.PRICE_BETWEEN.getQuery("200000", "400000")),
    PRICE_BETWEEN_400K_AND_600K  ("1006", ProductFilter.PRICE_BETWEEN.getQuery("400000", "600000")),
    PRICE_BETWEEN_600K_AND_800K  ("1007", ProductFilter.PRICE_BETWEEN.getQuery("600000", "800000")),
    PRICE_BETWEEN_800K_AND_1000K ("1008", ProductFilter.PRICE_BETWEEN.getQuery("800000", "1000000")),
    PRICE_BETWEEN_1000K_AND_1200K("1009", ProductFilter.PRICE_BETWEEN.getQuery("1000000", "1200000")),
    PRICE_BETWEEN_1200K_AND_1400K("1010", ProductFilter.PRICE_BETWEEN.getQuery("1200000", "1400000")),
    PRICE_BETWEEN_1400K_AND_1600K("1011", ProductFilter.PRICE_BETWEEN.getQuery("1400000", "1600000")),
    PRICE_BETWEEN_1600K_AND_1800K("1012", ProductFilter.PRICE_BETWEEN.getQuery("1600000", "1800000")),
    PRICE_BETWEEN_1800K_AND_2000K("1013", ProductFilter.PRICE_BETWEEN.getQuery("1800000", "2000000")),

    SIZE_BETWEEN_4_AND_5("1014", ProductFilter.SIZE_BETWEEN.getQuery("4", "5")),
    SIZE_BETWEEN_5_AND_6("1015", ProductFilter.SIZE_BETWEEN.getQuery("5", "6")),
    SIZE_BETWEEN_6_AND_7("1016", ProductFilter.SIZE_BETWEEN.getQuery("6", "7")),
    SIZE_BETWEEN_7_AND_8("1017", ProductFilter.SIZE_BETWEEN.getQuery("7", "8")),

    PROCESSOR_EQ_SNAPDRAGON_8_GEN_2     ("1018", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8 Gen2%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8_PLUS_GEN_1("1019", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8+ Gen1%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8_GEN_1     ("1020", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8 Gen1%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8XX         ("1021", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8%", null).and(QDetail.detail.detailContent.notLike("%Gen%"))),
    PROCESSOR_EQ_SNAPDRAGON_7XX         ("1022", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤7%", null)),
    PROCESSOR_EQ_SNAPDRAGON_6XX         ("1023", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤6%", null)),
    PROCESSOR_EQ_A16                    ("1024", ProductFilter.PROCESSOR_EQ.getQuery("A16%", null)),
    PROCESSOR_EQ_A15                    ("1025", ProductFilter.PROCESSOR_EQ.getQuery("A15%", null)),
    PROCESSOR_EQ_A14                    ("1026", ProductFilter.PROCESSOR_EQ.getQuery("A14%", null)),
    PROCESSOR_EQ_A13                    ("1027", ProductFilter.PROCESSOR_EQ.getQuery("A13%", null)),
    PROCESSOR_EQ_DIMENSITY              ("1028", ProductFilter.PROCESSOR_EQ.getQuery("디맨시티%", null)),
    PROCESSOR_EQ_EXYNOS                 ("1029", ProductFilter.PROCESSOR_EQ.getQuery("엑시노스%", null)),
    PROCESSOR_EQ_HELIO                  ("1030", ProductFilter.PROCESSOR_EQ.getQuery("Helio%", null)),
    PROCESSOR_EQ_MEDIATEK               ("1031", ProductFilter.PROCESSOR_EQ.getQuery("미디어텍%", null)),

    RAM_EQ_2GB ("1032", ProductFilter.RAM_EQ.getQuery("2GB", null)),
    RAM_EQ_3GB ("1033", ProductFilter.RAM_EQ.getQuery("3GB", null)),
    RAM_EQ_4GB ("1034", ProductFilter.RAM_EQ.getQuery("4GB", null)),
    RAM_EQ_6GB ("1035", ProductFilter.RAM_EQ.getQuery("6GB", null)),
    RAM_EQ_8GB ("1036", ProductFilter.RAM_EQ.getQuery("8GB", null)),
    RAM_EQ_12GB("1037", ProductFilter.RAM_EQ.getQuery("12GB", null)),
    RAM_EQ_16GB("1038", ProductFilter.RAM_EQ.getQuery("16GB", null)),

    MEMORY_EQ_32GB ("1039", ProductFilter.MEMORY_EQ.getQuery("32GB", null)),
    MEMORY_EQ_64GB ("1040", ProductFilter.MEMORY_EQ.getQuery("64GB", null)),
    MEMORY_EQ_128GB("1041", ProductFilter.MEMORY_EQ.getQuery("128GB", null)),
    MEMORY_EQ_256GB("1042", ProductFilter.MEMORY_EQ.getQuery("256GB", null)),
    MEMORY_EQ_512GB("1043", ProductFilter.MEMORY_EQ.getQuery("512GB", null)),
    MEMORY_EQ_1TB  ("1044", ProductFilter.MEMORY_EQ.getQuery("1TB", null));
    private String code;
    private BooleanExpression filter;

    public static BooleanExpression getFilter(String code){
        return Arrays.stream(ProductFilterDetail.values()).filter(i -> i.code.equals(code)).findAny().get().getFilter();
    }
}
