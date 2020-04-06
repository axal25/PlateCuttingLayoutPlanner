package ui.center.factory;

import ui.pop.up.ErrorPopUpUI;

public class CenterOptionFactory {
    public static final String[] STRING_CODES = {
            "Home",
            "Laboratory #1",
            "Laboratory #2",
            "Lambda expressions",
            "\"Mayeryn\" recruitment",
    };

    private static CenterOption getCenterOption(String stringCode) throws Exception {
        if(CenterOptionFactory.STRING_CODES[0].compareTo(stringCode) == 0) return CenterOption.HOME;
        else if(CenterOptionFactory.STRING_CODES[1].compareTo(stringCode) == 0) return CenterOption.LABORATORY_1;
        else if(CenterOptionFactory.STRING_CODES[2].compareTo(stringCode) == 0) return CenterOption.LABORATORY_2;
        else if(CenterOptionFactory.STRING_CODES[3].compareTo(stringCode) == 0) return CenterOption.LAMBDA_EXPRESSIONS;
        else if(CenterOptionFactory.STRING_CODES[4].compareTo(stringCode) == 0) return CenterOption.MAYERYN_RECRUITMENT;
        else throw new Exception("Unsupported String: \"" + stringCode + "\"");
    }

    public static CenterOption getSafelyCenterOption(String stringCode, CenterOption fallbackCenterOption) {
        try {
            return getCenterOption(stringCode);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopUpUI errorPopUpUI = new ErrorPopUpUI("Error during shortening of file", e.getMessage());
            return fallbackCenterOption;
        }
    }
}
