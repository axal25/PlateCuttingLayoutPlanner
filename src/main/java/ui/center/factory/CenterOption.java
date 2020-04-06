package ui.center.factory;

public enum CenterOption {
    HOME (CenterOptionFactory.STRING_CODES[0]),
    LABORATORY_1 (CenterOptionFactory.STRING_CODES[1]),
    LABORATORY_2 (CenterOptionFactory.STRING_CODES[2]),
    LAMBDA_EXPRESSIONS (CenterOptionFactory.STRING_CODES[3]),
    MAYERYN_RECRUITMENT (CenterOptionFactory.STRING_CODES[4]);

    private String stringCode;

    private CenterOption(String stringCode) {
        this.stringCode = stringCode;
    }

    @Override
    public String toString() {
        return this.stringCode;
    }
}
