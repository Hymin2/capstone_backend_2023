package ac.kr.tukorea.capstone.config.util;

import ac.kr.tukorea.capstone.product.entity.QDetail;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProductFilterDetail {
    COMPANY_EQ_SAMSUNG_PHONE("P1001", ProductFilter.COMPANY_EQ.getQuery("삼성전자", null)),
    COMPANY_EQ_APPLE_PHONE  ("P1002", ProductFilter.COMPANY_EQ.getQuery("APPLE", null)),
    COMPANY_EQ_ETC_PHONE    ("P1003", ProductFilter.COMPANY_ETC.getQuery(null, null)),

    PRICE_BETWEEN_0K_AND_200K_PHONE    ("P1004", ProductFilter.PRICE_BETWEEN.getQuery("0", "200000")),
    PRICE_BETWEEN_200K_AND_400K_PHONE  ("P1005", ProductFilter.PRICE_BETWEEN.getQuery("200000", "400000")),
    PRICE_BETWEEN_400K_AND_600K_PHONE  ("P1006", ProductFilter.PRICE_BETWEEN.getQuery("400000", "600000")),
    PRICE_BETWEEN_600K_AND_800K_PHONE  ("P1007", ProductFilter.PRICE_BETWEEN.getQuery("600000", "800000")),
    PRICE_BETWEEN_800K_AND_1000K_PHONE ("P1008", ProductFilter.PRICE_BETWEEN.getQuery("800000", "1000000")),
    PRICE_BETWEEN_1000K_AND_1200K_PHONE("P1009", ProductFilter.PRICE_BETWEEN.getQuery("1000000", "1200000")),
    PRICE_BETWEEN_1200K_AND_1400K_PHONE("P1010", ProductFilter.PRICE_BETWEEN.getQuery("1200000", "1400000")),
    PRICE_BETWEEN_1400K_AND_1600K_PHONE("P1011", ProductFilter.PRICE_BETWEEN.getQuery("1400000", "1600000")),
    PRICE_BETWEEN_1600K_AND_1800K_PHONE("P1012", ProductFilter.PRICE_BETWEEN.getQuery("1600000", "1800000")),
    PRICE_BETWEEN_1800K_AND_2000K_PHONE("P1013", ProductFilter.PRICE_BETWEEN.getQuery("1800000", "2000000")),

    SIZE_BETWEEN_4_AND_5_PHONE("P1014", ProductFilter.SIZE_BETWEEN.getQuery("4", "5")),
    SIZE_BETWEEN_5_AND_6_PHONE("P1015", ProductFilter.SIZE_BETWEEN.getQuery("5", "6")),
    SIZE_BETWEEN_6_AND_7_PHONE("P1016", ProductFilter.SIZE_BETWEEN.getQuery("6", "7")),
    SIZE_BETWEEN_7_AND_8_PHONE("P1017", ProductFilter.SIZE_BETWEEN.getQuery("7", "8")),

    PROCESSOR_EQ_SNAPDRAGON_8_GEN_2_PHONE     ("P1018", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8 Gen2%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8_PLUS_GEN_1_PHONE("P1019", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8+ Gen1%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8_GEN_1_PHONE     ("P1020", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8 Gen1%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8XX_PHONE         ("P1021", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8%", null).and(QDetail.detail.detailContent.notLike("%Gen%"))),
    PROCESSOR_EQ_SNAPDRAGON_7XX_PHONE         ("P1022", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤7%", null)),
    PROCESSOR_EQ_SNAPDRAGON_6XX_PHONE         ("P1023", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤6%", null)),
    PROCESSOR_EQ_A16_PHONE                    ("P1024", ProductFilter.PROCESSOR_EQ.getQuery("A16%", null)),
    PROCESSOR_EQ_A15_PHONE                    ("P1025", ProductFilter.PROCESSOR_EQ.getQuery("A15%", null)),
    PROCESSOR_EQ_A14_PHONE                    ("P1026", ProductFilter.PROCESSOR_EQ.getQuery("A14%", null)),
    PROCESSOR_EQ_A13_PHONE                    ("P1027", ProductFilter.PROCESSOR_EQ.getQuery("A13%", null)),
    PROCESSOR_EQ_DIMENSITY_PHONE              ("P1028", ProductFilter.PROCESSOR_EQ.getQuery("디맨시티%", null)),
    PROCESSOR_EQ_EXYNOS_PHONE                 ("P1029", ProductFilter.PROCESSOR_EQ.getQuery("엑시노스%", null)),
    PROCESSOR_EQ_HELIO_PHONE                  ("P1030", ProductFilter.PROCESSOR_EQ.getQuery("Helio%", null)),
    PROCESSOR_EQ_MEDIATEK_PHONE               ("P1031", ProductFilter.PROCESSOR_EQ.getQuery("미디어텍%", null)),

    RAM_EQ_2GB_PHONE ("P1032", ProductFilter.RAM_EQ.getQuery("2GB", null)),
    RAM_EQ_3GB_PHONE ("P1033", ProductFilter.RAM_EQ.getQuery("3GB", null)),
    RAM_EQ_4GB_PHONE ("P1034", ProductFilter.RAM_EQ.getQuery("4GB", null)),
    RAM_EQ_6GB_PHONE ("P1035", ProductFilter.RAM_EQ.getQuery("6GB", null)),
    RAM_EQ_8GB_PHONE ("P1036", ProductFilter.RAM_EQ.getQuery("8GB", null)),
    RAM_EQ_12GB_PHONE("P1037", ProductFilter.RAM_EQ.getQuery("12GB", null)),
    RAM_EQ_16GB_PHONE("P1038", ProductFilter.RAM_EQ.getQuery("16GB", null)),

    MEMORY_EQ_32GB_PHONE ("P1039", ProductFilter.MEMORY_EQ.getQuery("32GB", null)),
    MEMORY_EQ_64GB_PHONE ("P1040", ProductFilter.MEMORY_EQ.getQuery("64GB", null)),
    MEMORY_EQ_128GB_PHONE("P1041", ProductFilter.MEMORY_EQ.getQuery("128GB", null)),
    MEMORY_EQ_256GB_PHONE("P1042", ProductFilter.MEMORY_EQ.getQuery("256GB", null)),
    MEMORY_EQ_512GB_PHONE("P1043", ProductFilter.MEMORY_EQ.getQuery("512GB", null)),
    MEMORY_EQ_1TB_PHONE  ("P1044", ProductFilter.MEMORY_EQ.getQuery("1TB", null)),


    COMPANY_EQ_SAMSUNG_TABLET("T2001", ProductFilter.COMPANY_EQ.getQuery("삼성전자", null)),
    COMPANY_EQ_APPLE_TABLET  ("T2002", ProductFilter.COMPANY_EQ.getQuery("APPLE", null)),
    COMPANY_EQ_MICROSOFT_TABLET  ("T2003", ProductFilter.COMPANY_EQ.getQuery("Microsoft", null)),
    COMPANY_EQ_LENOVO_TABLET  ("T2004", ProductFilter.COMPANY_EQ.getQuery("레노버", null)),
    COMPANY_EQ_ETC_TABLET    ("T2005", ProductFilter.COMPANY_ETC.getQuery(null, null)),

    PRICE_BETWEEN_0K_AND_200K_TABLET    ("T2006", ProductFilter.PRICE_BETWEEN.getQuery("0", "200000")),
    PRICE_BETWEEN_200K_AND_400K_TABLET  ("T2007", ProductFilter.PRICE_BETWEEN.getQuery("200000", "400000")),
    PRICE_BETWEEN_400K_AND_600K_TABLET  ("T2008", ProductFilter.PRICE_BETWEEN.getQuery("400000", "600000")),
    PRICE_BETWEEN_600K_AND_800K_TABLET  ("T2009", ProductFilter.PRICE_BETWEEN.getQuery("600000", "800000")),
    PRICE_BETWEEN_800K_AND_1000K_TABLET ("T2010", ProductFilter.PRICE_BETWEEN.getQuery("800000", "1000000")),
    PRICE_BETWEEN_1000K_AND_1200K_TABLET("T2011", ProductFilter.PRICE_BETWEEN.getQuery("1000000", "1200000")),
    PRICE_BETWEEN_1200K_AND_1400K_TABLET("T2012", ProductFilter.PRICE_BETWEEN.getQuery("1200000", "1400000")),
    PRICE_BETWEEN_1400K_AND_1600K_TABLET("T2013", ProductFilter.PRICE_BETWEEN.getQuery("1400000", "1600000")),
    PRICE_BETWEEN_1600K_AND_1800K_TABLET("T2014", ProductFilter.PRICE_BETWEEN.getQuery("1600000", "1800000")),
    PRICE_BETWEEN_1800K_AND_2000K_TABLET("T2015", ProductFilter.PRICE_BETWEEN.getQuery("1800000", "2000000")),

    SIZE_BETWEEN_0_AND_7_TABLET  ("T2016", ProductFilter.SIZE_BETWEEN.getQuery("0", "7")),
    SIZE_BETWEEN_7_AND_8_TABLET  ("T2017", ProductFilter.SIZE_BETWEEN.getQuery("7", "8")),
    SIZE_BETWEEN_8_AND_9_TABLET  ("T2018", ProductFilter.SIZE_BETWEEN.getQuery("8", "9")),
    SIZE_BETWEEN_9_AND_10_TABLET ("T2019", ProductFilter.SIZE_BETWEEN.getQuery("9", "10")),
    SIZE_BETWEEN_10_AND_11_TABLET("T2020", ProductFilter.SIZE_BETWEEN.getQuery("10", "11")),
    SIZE_BETWEEN_11_AND_12_TABLET("T2021", ProductFilter.SIZE_BETWEEN.getQuery("11", "12")),
    SIZE_BETWEEN_12_AND_13_TABLET("T2022", ProductFilter.SIZE_BETWEEN.getQuery("12", "13")),
    SIZE_BETWEEN_13_AND_14_TABLET("T2023", ProductFilter.SIZE_BETWEEN.getQuery("13", "14")),
    SIZE_BETWEEN_14_AND_15_TABLET("T2024", ProductFilter.SIZE_BETWEEN.getQuery("14", "15")),
    SIZE_BETWEEN_15_AND_16_TABLET("T2025", ProductFilter.SIZE_BETWEEN.getQuery("15", "16")),
    SIZE_BETWEEN_16_AND_17_TABLET("T2026", ProductFilter.SIZE_BETWEEN.getQuery("16", "17")),
    SIZE_BETWEEN_17_AND_18_TABLET("T2027", ProductFilter.SIZE_BETWEEN.getQuery("17", "18")),
    SIZE_BETWEEN_18_AND_19_TABLET("T2028", ProductFilter.SIZE_BETWEEN.getQuery("18", "19")),

    PROCESSOR_EQ_M2_TABLET                     ("T2029", ProductFilter.PROCESSOR_EQ.getQuery("M2", null)),
    PROCESSOR_EQ_M1_TABLET                     ("T2030", ProductFilter.PROCESSOR_EQ.getQuery("M1", null)),
    PROCESSOR_EQ_A15_TABLET                    ("T2031", ProductFilter.PROCESSOR_EQ.getQuery("%A15%", null)),
    PROCESSOR_EQ_A14_TABLET                    ("T2032", ProductFilter.PROCESSOR_EQ.getQuery("%A14%", null)),
    PROCESSOR_EQ_A13_TABLET                    ("T2033", ProductFilter.PROCESSOR_EQ.getQuery("%A13%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8_GEN_1_TABLET     ("T2034", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8 Gen1%", null)),
    PROCESSOR_EQ_SNAPDRAGON_8XX_TABLET         ("T2035", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤8%", null).and(QDetail.detail.detailContent.notLike("%Gen%"))),
    PROCESSOR_EQ_SNAPDRAGON_7XX_TABLET         ("T2036", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤7%", null)),
    PROCESSOR_EQ_SNAPDRAGON_6XX_TABLET         ("T2037", ProductFilter.PROCESSOR_EQ.getQuery("스냅드래곤6%", null)),
    PROCESSOR_EQ_EXYNOS7_TABLET                ("T2038", ProductFilter.PROCESSOR_EQ.getQuery("%엑시노스9%", null)),
    PROCESSOR_EQ_EXYNOS9_TABLET                ("T2039", ProductFilter.PROCESSOR_EQ.getQuery("%엑시노스7%", null)),

    RAM_EQ_2GB_TABLET ("T2040", ProductFilter.RAM_EQ.getQuery("2GB", null)),
    RAM_EQ_3GB_TABLET ("T2041", ProductFilter.RAM_EQ.getQuery("3GB", null)),
    RAM_EQ_4GB_TABLET ("T2042", ProductFilter.RAM_EQ.getQuery("4GB", null)),
    RAM_EQ_6GB_TABLET ("T2043", ProductFilter.RAM_EQ.getQuery("6GB", null)),
    RAM_EQ_8GB_TABLET ("T2044", ProductFilter.RAM_EQ.getQuery("8GB", null)),
    RAM_EQ_12GB_TABLET("T2045", ProductFilter.RAM_EQ.getQuery("12GB", null)),
    RAM_EQ_16GB_TABLET("T2046", ProductFilter.RAM_EQ.getQuery("16GB", null)),

    MEMORY_EQ_32GB_TABLET ("T2047", ProductFilter.MEMORY_EQ.getQuery("32GB", null)),
    MEMORY_EQ_64GB_TABLET ("T2048", ProductFilter.MEMORY_EQ.getQuery("64GB", null)),
    MEMORY_EQ_128GB_TABLET("T2049", ProductFilter.MEMORY_EQ.getQuery("128GB", null)),
    MEMORY_EQ_256GB_TABLET("T2050", ProductFilter.MEMORY_EQ.getQuery("256GB", null)),
    MEMORY_EQ_512GB_TABLET("T2051", ProductFilter.MEMORY_EQ.getQuery("512GB", null)),
    MEMORY_EQ_1TB_TABLET  ("T2052", ProductFilter.MEMORY_EQ.getQuery("1TB", null));
    private String code;
    private BooleanExpression filter;

    public static BooleanExpression getFilter(String code){
        return Arrays.stream(ProductFilterDetail.values()).filter(i -> i.code.equals(code)).findAny().get().getFilter();
    }

    public static String getValue(String code){
        for(ProductFilterDetail productFilterDetail : ProductFilterDetail.values()){
            if(productFilterDetail.code.equals(code)) return productFilterDetail.name();
        }
        return null;
    }
}
